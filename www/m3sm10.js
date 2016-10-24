/**
 * JavaScript interface to abstract
 * the usage of the cordova Sygic Navigation plugin.
 *
 * @module com/dff/cordova/plugins/sygic
 */

'use strict';

var cordova = require('cordova');
var feature = "M3SM10";

function M3SM10() {};

var actions = [
    "onLog",
    "onBarcode",
    "scanStart",
    "scanDispose"
];

function createActionFunction (action) {
    return function (success, error, args) {
        cordova.exec(success, error, feature, action, [args]);
    }
}

actions.forEach(function (action) {
    M3SM10.prototype[action] = createActionFunction(action);
});

module.exports = new M3SM10();
