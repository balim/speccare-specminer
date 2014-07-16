module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        originalAssetsDir: 'assets',
        webDir: 'src/main/webapp',
        dumpAssetsDir: '<%= webDir %>/<%= originalAssetsDir %>',

        copy: {
            main: {
                files: [
                    {expand: true, src: ['<%= originalAssetsDir %>/**'], dest: '<%= webDir %>'}
                ]
            }
        },

        clean: {
            build: {
                src: ['<%= dumpAssetsDir %>/**']
            },
            less: {
                src: ['<%= dumpAssetsDir %>/less']
            }
        },

        requirejs: {
            main: {
                options: {
                    mainConfigFile: '<%= dumpAssetsDir %>/js/config.js',
                    appDir: '<%= originalAssetsDir %>',
                    baseUrl: './js',
                    dir: '<%= dumpAssetsDir %>',
                    optimizeCss: "none",
                    optimize: "none",
                    modules: [
                        {
                            name: 'config',
                            include: ['jquery', 'domReady', 'bootstrap']
                        },
                        {
                            name: 'app/main',
                            exclude: ['config']
                        }
                    ]
                }
            }
        },

        uglify: {
            options: {
                // info on top of every file
                banner: '/*! @@1 <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n',
                footer: '/*! @@2 <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> */\n',
            },
            build: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= dumpAssetsDir %>/js/app',
                        src: ['**/*.js'],
                        dest: '<%= dumpAssetsDir %>/js/app'
                    },
                    {
                        expand: true,
                        cwd: '<%= dumpAssetsDir %>/js/test',
                        src: ['**/*.js'],
                        dest: '<%= dumpAssetsDir %>/js/test'
                    }
                ]
            }
        },
        jshint: {
            options: {
                reporter: require('jshint-stylish')
            },
            all: [
                'Gruntfile.js',
                '<%= originalAssetsDir %>/js/app/**/*.js',
                '<%= originalAssetsDir %>/js/test/**/*.js'
            ]
        },
        less: {
            dev: {
                options: {
                    compress: false,
                    yuicompress: false
                },
                files: [{
                    expand: true,
                    cwd: '<%= dumpAssetsDir %>/less/',
                    src: ['*.less', '!{variables,mixin}*.less'],
                    dest: '<%= dumpAssetsDir %>/css/',
                    ext: '.css'
                }]
            },
            dist: {
                options: {
                    compress: true,
                    yuicompress: true,
                    optimization: 2
                },
                files: {
                    // target.css file: source.less file
                    '<%= dumpAssetsDir %>/css/main.css': '<%= dumpAssetsDir %>/less/*.less'
                }
            }
        },
        watch: {
            scripts: {
                files: ['<%= originalAssetsDir %>/js/app/**', '<%= originalAssetsDir %>/js/test/**'],
                    tasks: ['copy', 'jshint', 'karma:ci'],
                    options: {
                    spawn: false
                }
            },
            styles: {
                files: ['<%= originalAssetsDir %>/less/**/*.less', '<%= originalAssetsDir %>/img/**/*'],
                tasks: ['copy', 'less'],
                options: {
                    spawn: false
                }
            }
        },
        karma: {
            options: {
                configFile: 'assets/js/karma.conf.js'
            },
            tdd: {
                autoWatch: true
            },
            ci: {
                singleRun: true
            },
            full: {
                browsers: ['PhantomJS', 'Chrome', 'Firefox']
            },
            start: {
                autoWatch: true
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-karma');


    grunt.registerTask('publishAssets', ['clean:build', 'copy']);


    grunt.registerTask('default', ['publishAssets', 'jshint', 'karma:ci', 'less:dev', 'clean:less']);

    grunt.registerTask('tdd', ['default', 'watch']);


    grunt.registerTask('production', ['publishAssets', 'jshint', 'karma:ci', 'requirejs', 'uglify', 'less:dist', 'clean:less']);
};
