## Changelogs

- **[3.3.3]**
  - loginWithKakaoAccount 추가 [#239](https://github.com/react-native-seoul/react-native-kakao-login/pull/239)

- **[3.3.1]**
  - Resolve [#222](https://github.com/react-native-seoul/react-native-kakao-login/issues/222)

- **[3.3.0]**
  - Fix regression in [#221](https://github.com/react-native-seoul/react-native-kakao-login/issues/221), [#222](https://github.com/react-native-seoul/react-native-kakao-login/issues/222)

- **[3.2.2]**
  - HotFix - `getProfile` [#226](https://github.com/react-native-seoul/react-native-kakao-login/pull/226)

- **[3.2.1]**
  - Resolve [#222](https://github.com/react-native-seoul/react-native-kakao-login/issues/222)

- **[3.2.0]**
  - Android Kakao SDK upgrade `2.5.0`.
  - Hotfix on [#217](https://github.com/react-native-seoul/react-native-kakao-login/issues/217)

- **[3.1.+]**
  - Android `kotlin` 라이브러리로 변환.

- **[3.0.7]**
  - `KakaoProfile` 타입 추가 [#207](https://github.com/react-native-seoul/react-native-kakao-login/pull/207).

- **[3.0.6]**
  - `isKakaoTalkLoginUrl` 에서 SDK가 준비가 되지 않았으면 `false` return 하도록 수정.

- **[3.0.5]**
  - `3.0.4`에서 잘못 수정된 부분 수정 (regression).

- **[3.0.4]**
  - Bugfix: iOS 백그라운드에서 진입시 sdk가 준비가 안되었을 때 튕기는 오류 수정.

- **[3.0.1]**
  - Bugfix: `unlink`가 `logout`과 동일하게 구현되어 있는 부분을 수정했습니다.

- **[3.0.0]**
  - 카카오 SDK V2를 지원합니다 :tada:. 위 버전에서는 `2.4.+` 버전을 쓰고 있습니다. 모든 API들이 새로 업데이트 되었습니다. 기존의 [README](https://github.com/react-native-seoul/react-native-kakao-login/blob/master/README.md)는 [README_DEPRECATED](https://github.com/react-native-seoul/react-native-kakao-login/blob/master/README_DEPRECATED.md) 문서로 이전하였고 새롭게 업데이트 되었습니다.

- **[2.10.2]**

  - 안드로이드 릴리즈 빌드 버그 수정  [#179](https://github.com/react-native-seoul/react-native-kakao-login/pull/179)

- **[2.10.1]**

  - 연령대 권한 없을 경우 null 처리  [#167](https://github.com/react-native-seoul/react-native-kakao-login/pull/167)

  - 예제프로젝트 업데이트  [#169](https://github.com/react-native-seoul/react-native-kakao-login/pull/169)

- **[2.10.0]**

  - 연령대 연동 정보 추가  [#163](https://github.com/react-native-seoul/react-native-kakao-login/pull/163)

  - login메서드에 인증 방법 선택 옵션 추가  [#164](https://github.com/react-native-seoul/react-native-kakao-login/pull/164)

- **[2.9.0]**

  - getTokens for getting accessToken and refreshToken  [#160](https://github.com/react-native-seoul/react-native-kakao-login/pull/160)

- **[2.8.0]**

  - Always add callbacks in onHostResume() [#156](https://github.com/react-native-seoul/react-native-kakao-login/pull/156)

  - Make KakaoSDK.init() lazy [#157](https://github.com/react-native-seoul/react-native-kakao-login/pull/157)

- **[2.7.0]**

  - [Android] 동의 항목이 존재하지 않는 경우, me.account 및 me.account.profile 데이터가 null 로 들어오는 이슈 수정 [#151](https://github.com/react-native-seoul/react-native-kakao-login/pull/151)

  - [iOS] 동의 항목이 존재하지 않는 경우,scopes 가 nil 로 들어오는 이슈 수정 [#151](https://github.com/react-native-seoul/react-native-kakao-login/pull/151)

  - [iOS] 구버전 SDK에서 이미 세션을 생성(로그인) 한 상태에서 최신 SDK 로 업데이트 한 경우, refreshTokenExpiresAt 가 nil 로 들어오는 이슈 수정 [#151](https://github.com/react-native-seoul/react-native-kakao-login/pull/151)

  - Add birthday and gender to IProfile [#148](https://github.com/react-native-seoul/react-native-kakao-login/pull/148)

- **[2.6.0]**

  - 추가 동의 요청 기능에 대한 구현 [#144](https://github.com/react-native-seoul/react-native-kakao-login/pull/144)

- **[2.5.0]**

  - 연결끊기 메소드 구현 [#134](https://github.com/react-native-seoul/react-native-kakao-login/pull/134)

- **[2.4.5]**

  - KakaoOpenSDK 수동설치 관련 문서수정 [#129](https://github.com/react-native-seoul/react-native-kakao-login/pull/129)
  - 라이프 사이클 이벤트 리스너 등록 [#130](https://github.com/react-native-seoul/react-native-kakao-login/pull/130)

- **[2.4.4]**

  - update readme [#121](https://github.com/react-native-seoul/react-native-kakao-login/pull/121)
  - implement onHostResume in android [#126](https://github.com/react-native-seoul/react-native-kakao-login/pull/126)

- **[2.4.3]**

  - Add birthyear information in the GetProfile method [#102](https://github.com/react-native-seoul/react-native-kakao-login/pull/118)

- **[2.4.2]**

  - upgrade sdk versions for bugfix [#116](https://github.com/react-native-seoul/react-native-kakao-login/pull/116)

- **[2.4.1]**

  - fix typo in readme [#104](https://github.com/react-native-seoul/react-native-kakao-login/pull/104)

- **[2.4.0]**

  - Add gender and birthday information in the GetProfile method [#102](https://github.com/react-native-seoul/react-native-kakao-login/pull/102)

- **[2.3.0]**

  - implement onCancelHandler on android [#96](https://github.com/react-native-seoul/react-native-kakao-login/pull/96)

- **[2.2.0]**

  - upgrade kakao sdk version (ios: 1.20.0, android: 1.26.0) [#93](https://github.com/react-native-seoul/react-native-kakao-login/pull/93)

* **[2.1.2]**

  - fix typo in `readme` [#86](https://github.com/react-native-seoul/react-native-kakao-login/pull/89)

* **[2.1.1]**

  - fix typo in `readme` [#86](https://github.com/react-native-seoul/react-native-kakao-login/pull/86)
  - update kakaoSDK version [#86](https://github.com/react-native-seoul/react-native-kakao-login/pull/86)
  - fix typo in `RNKakaoLoginsModule.java` [#83](https://github.com/react-native-seoul/react-native-kakao-login/pull/83)

* **[2.1.0]**

  - Add token info properties at login[#77](https://github.com/react-native-seoul/react-native-kakao-login/pull/77)

- **[2.0.0]**

  - support promises [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)
  - support error code partially [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)
  - update example code [#67](https://github.com/react-native-seoul/react-native-kakao-login/pull/67)

- **[1.0.1-rc-2]**

  - fix broken link url in document [#70](https://github.com/react-native-seoul/react-native-kakao-login/pull/70)
  - change podspec name [#70](https://github.com/react-native-seoul/react-native-kakao-login/pull/70)

* **[1.0.0-rc-2]**

  - Migrate/typescript [#69](https://github.com/react-native-seoul/react-native-kakao-login/pull/69)
  - Migrate to @react-native-seoul/kakao-login [#68](https://github.com/react-native-seoul/react-native-kakao-login/pull/68)

* **[1.4.0]**

  - Upgrade android kakaosdk to 1.23.0. [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Upgrade ios kakaosdk to 1.16.0. [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Support AutoLink for RN >= 0.60 [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - bug fix about Logout API [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Update Example code [#55](https://github.com/react-native-seoul/react-native-kakao-login/pull/55)
  - Add .npmignore [#61](https://github.com/react-native-seoul/react-native-kakao-login/pull/61)
  - Use Native Map and Dictionary for javascript object [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)
  - Rename profile properties [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)
  - Support real-time profile [#65](https://github.com/react-native-seoul/react-native-kakao-login/pull/65)

* **[1.3.7]**

  - Upgrade android kakaosdk to 1.15.1.
  - Fix security issue [#37](https://github.com/react-native-seoul/react-native-kakao-login/pull/37)
  - Update gradle setup [#36](https://github.com/react-native-seoul/react-native-kakao-login/pull/36)
  - Safe null check for KakaoAccount [#40](https://github.com/react-native-seoul/react-native-kakao-login/pull/40)
  - Better error handling in `example` [#41](https://github.com/react-native-seoul/react-native-kakao-login/pull/41)
  - Add header seaerch path for pod project [#49](https://github.com/react-native-seoul/react-native-kakao-login/pull/49)

* **[1.2.1]**

  - Make sure to check whether KakaoSDK already has an adapter.

* **[1.2.0]**

  - Changed the way to import Kakao SDK. Do not need to extend `GlobalApplication`.

* **[1.1.0]**

  - Prevent version miss match problem with other libraries when conflict exists

* **[1.0.3]**

  - Fixed crashing when going back canceling login

* **[1.0.2]**

  - Edit to use new methods and properly return result
  - Edit to parse native callback result
  - Edit to return values in proper json types
    Export interfaces for users to use in their own codes

* **[1.0.1]**
  - Add installation info about framework search path
  - Add steps about adding url callback for ios
