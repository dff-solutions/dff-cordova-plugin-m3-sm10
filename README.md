# dff-cordova-plugin-m3-sm10
M3 Mobile SM10 SDK cordova plugin

## Reference
 + [M3 Mobile](http://www.m3mobile.net/)

## Supported Platforms
 + Android

## Installation
    cordova plugin add https://github.com/dff-solutions/dff-cordova-plugin-m3-sm10.git

## Usage

### onBarcode
```javascript
/**
 * Called each time a barcode was scanned.
 *
 * @function
 * @name onBarcode
 * @param {function} success Callback for success
 * @param {function} error Callback for error
 */
 M3SM10
    .onBarcode(function (barcode) {
        console.log(barcode);
    }, function (reason) {
        console.error(reason);
    });
```

### scanStart
```javascript
/**
 * Start scan manually.
 *
 * @function
 * @name scanStart
 * @param {function} success Callback for success
 * @param {function} error Callback for error
 */
 M3SM10
    .scanStart(function () {
        console.log("started");
    }, function (reason) {
        console.error(reason);
    });
```

### scanDispose
```javascript
/**
 * Dispose scan manually.
 *
 * @function
 * @name scanDispose
 * @param {function} success Callback for success
 * @param {function} error Callback for error
 */
 M3SM10
    .scanDispose(function () {
        console.log("disposed");
    }, function (reason) {
        console.error(reason);
    });
```

## Documentation
- <a href="https://dff-solutions.github.io/dff-cordova-plugin-m3-sm10/" target="_blank" >JAVA DOC</a>
