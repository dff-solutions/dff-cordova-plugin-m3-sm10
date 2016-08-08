# dff-cordova-plugin-m3-sm10
M3 Mobile SM10 SDK cordova plugin

## Supported Platforms
- Android


## Installation
    cordova plugin add https://github.com/frankkoenigstein/dff-cordova-plugin-m3-sm10.git

  
## Usage
    M3SM10.onBarcode(function (barcode) {
        console.log(barcode);
      }, function (reason) {
        console.error(reason);
      });
