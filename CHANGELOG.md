## Changelogs

- **[2.1.1]**

  - fix typo in `readme` [#86](https://github.com/react-native-seoul/react-native-kakao-login/pull/86)
  - update kakaoSDK version [#86](https://github.com/react-native-seoul/react-native-kakao-login/pull/86)
  - fix typo in `RNKakaoLoginsModule.java` [#83](https://github.com/react-native-seoul/react-native-kakao-login/pull/83)

- **[2.1.0]**

  - Add token info properties at login[#77](https://github.com/react-native-seoul/react-native-kakao-login/pull/77)

* **[2.0.0]**

  - support promises [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)
  - support error code partially [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)
  - update example code [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)

* **[1.0.1-rc-2]**

  - fix broken link url in document [#70](https://github.com/react-native-seoul/react-native-kakao-login/pull/70)
  - change podspec name [#70](https://github.com/react-native-seoul/react-native-kakao-login/pull/70)

- **[1.0.0-rc-2]**

  - Migrate/typescript [#69](https://github.com/react-native-seoul/react-native-kakao-login/pull/69)
  - Migrate to @react-native-seoul/kakao-login [#68](https://github.com/react-native-seoul/react-native-kakao-login/pull/68)

- **[1.4.0]**

  - Upgrade android kakaosdk to 1.23.0. [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Upgrade ios kakaosdk to 1.16.0. [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Support AutoLink for RN >= 0.60 [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - bug fix about Logout API [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Update Example code [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Add .npmignore [#61](https://github.com/react-native-seoul/react-native-kakao-login/pull/61)
  - Use Native Map and Dictionary for javascript object [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)
  - Rename profile properties [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)
  - Support real-time profile [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)

- **[1.3.7]**

  - Upgrade android kakaosdk to 1.15.1.
  - Fix security issue [#37](https://github.com/react-native-seoul/react-native-kakao-login/pull/37)
  - Update gradle setup [#36](https://github.com/react-native-seoul/react-native-kakao-login/pull/36)
  - Safe null check for KakaoAccount [#40](https://github.com/react-native-seoul/react-native-kakao-login/pull/40)
  - Better error handling in `example` [#41](https://github.com/react-native-seoul/react-native-kakao-login/pull/41)
  - Add header seaerch path for pod project [#49](https://github.com/react-native-seoul/react-native-kakao-login/pull/49)

- **[1.2.1]**

  - Make sure to check whether KakaoSDK already has an adapter.

- **[1.2.0]**

  - Changed the way to import Kakao SDK. Do not need to extend `GlobalApplication`.

- **[1.1.0]**

  - Prevent version miss match problem with other libraries when conflict exists

- **[1.0.3]**

  - Fixed crashing when going back canceling login

- **[1.0.2]**

  - Edit to use new methods and properly return result
  - Edit to parse native callback result
  - Edit to return values in proper json types
    Export interfaces for users to use in their own codes

- **[1.0.1]**
  - Add installation info about framework search path
  - Add steps about adding url callback for ios
