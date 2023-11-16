# SimCards Flutter Plugin

<?code-excerpt path-base="example/lib"?>

A Flutter plugin that allows you to query device active SimCards.

Note that his plugin works only supports Android as it uses Telephony SubscriptionManager which is a package provided by Android

|             | Android |
| ----------- | ------- |
| **Support** | SDK 23+ |

## Setup

To use this plugin you need to add `READ_PHONE_STATE` permission in the `AndroidManifest.xml` file. There are `debug`, `main` and `profile` versions which are chosen depending on how you start your app. In general, it's sufficient to add permission only to the `main` version.

```xml
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <!-- NOTE: Since Android SDK 30, the `READ_PHONE_STATE` permission changed to `READ_PHONE_NUMBERS` -->
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS" />
    <application>
    ...
```

## How to use

First you need to request for the previously added `READ_PHONE_STATE` permission.

```dart
final _simcards = Simcards();

await _simcards.requestPermission();
```

Next you have to check if the `READ_PHONE_STATE` permission has been granted

```dart
bool _permissionGranted = await _simcards.hasPermission();
```

Now you can get all active `SimCard`s available on the device.

```dart
List<SimCard> _simCardList = await _simcards.getSimCards();
```

It is just as simple as that.

Supporting Cybroid Open Source Projects
---------------------------------------
Use the link below to support the continuous development of open source software by Cybroid Team and Community

<a href="https://www.buymeacoffee.com/drexsoftorg" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a>

Contributing to Cybroid Open Source Projects
--------------------------------------------

Cybroid welcomes contributions to our [open source projects on Github](http://github.com/cybroidtech/). When contributing, please follow the [Cybroid Community Code of Conduct](https://github.com/cybroidtech/simcards/blob//CODE_OF_CONDUCT.md).

Issues
------

Feel free to submit issues and enhancement requests [here](https://github.com/cybroidtech/simcards/issues).

Contributing
------------

Please refer to each project's style and contribution guidelines for submitting patches and additions. In general, we follow the "fork-and-pull" Git workflow.

 1. **Fork** the repo on GitHub
 2. **Clone** the project to your own machine
 3. **Commit** changes to your own branch
 4. **Push** your work back up to your fork
 5. Submit a **Pull request** so that we can review your changes

NOTE: Be sure to merge the latest from "upstream" before making a pull request!

Copyright and Licensing
-----------------------
```
MIT License

Copyright (c) 2023 Cybroid Technologies (U) Ltd

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
