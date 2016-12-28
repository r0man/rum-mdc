'use strict';

var gulp = require('gulp');
var sass = require('gulp-sass');

var scssIncludes = [
    'node_modules',
    'node_modules/material-components-web'
];

gulp.task('scss', function () {
    return gulp.src('./scss/**/*.scss')
        .pipe(sass({includePaths: scssIncludes}).on('error', sass.logError))
        .pipe(gulp.dest('resources/public/css'));
});

gulp.task('watch', function () {
    gulp.watch('./scss/**/*.scss', ['scss']);
});

gulp.task('default', ['scss']);
