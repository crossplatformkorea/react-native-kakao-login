# @react-native-seoul/kakao-login

<img src="image/main_banner.png" width="100%" height="auto" />
<p align="left">
  <a href="https://npmjs.org/package/@react-native-seoul/kakao-login">
    <img alt="npm version" src="http://img.shields.io/npm/v/@react-native-seoul/kakao-login.svg?style=flat-square">
  </a>
  <a href="https://npmjs.org/package/@react-native-seoul/kakao-login">
    <img src="http://img.shields.io/npm/dm/@react-native-seoul/kakao-login.svg?style=flat-square">
  </a>
  <a href="https://npmjs.org/package/@react-native-seoul/kakao-login">
    <img src="http://img.shields.io/npm/l/@react-native-seoul/kakao-login.svg?style=flat-square">
  </a>
</p>

React Native 카카오 로그인 라이브러리 입니다. `@react-native-seoul/kakao-login` < 3.0 이하 버전을 쓰시는 분들은 [DEPRECATED README][https://github.com/react-native-seoul/react-native-kakao-login/blob/master/README_DEPRECATED.md]를 참고해주세요.

세부 예제는 KakaoLoginExample 폴더 안의 예제 프로젝트를 확인해주세요.
해당 라이브러리는 `flow`와 `typescript`를 지원합니다.

## Getting started

해당 라이브러리는 `3.0.0` 이후 부터는 react-native `0.61`이상을 지원합니다. 카카오 라이브러리 지원이 아래 버전부터는 지원이 끊길 예정이므로 참고해주시기 바랍니다. 과거에는 [카카오 라이브러리 레거시 iOS](https://developers.kakao.com/docs/latest/ko/kakaologin/ios-v1)와 [카카오 라이브러리 레거시 Android](https://developers.kakao.com/docs/latest/ko/kakaologin/android-v1) 버전을 쓰고 있었습니다.

### Module Installation

##### RN >= 0.60

React Native 0.60.X이상부터는 `Auto linking`을 지원합니다. 따라서 설치는 매우 간편합니다.

iOS의 경우 `yarn add @react-native-seoul/kakao-login` 이후 `npx pod-install` 명령어로 pod 라이브러리만 추가로 설치해주시면 됩니다.

## Post Installation
> 설치가 제대로 되지 않는다면 example project의 설정을 참고하세요 👍

#### iOS

1. ios 카카오 sdk 설치 후의 설정과 관련해서는 [공식문서 - 카카오 로그인 > 설정하기](https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite)를 참고해주세요. 해당 가이드를 통해 카카오 개발자 페이지에서 본인의 어플리케이션을 생성해주세요.
2. Project => Targets 아래 앱 선택 => General 탭으로 이동해서 Bundle Identifier가 본인의 카카오 앱과 동일한지 확인해주세요.
3. [공식문서 - 개발 프로젝트 설정](https://developers.kakao.com/docs/latest/ko/getting-started/sdk-ios-v1) 을 참고하여 `info.plist`, `URL Types` 및 커스텀 스킴 추가 등 기타 필요한 세팅들을 프로젝트에 추가해줍니다.

#### Android

1. 안드로이드에서는 카카오 SDK가 모듈의 gradle 경로에 잡혀있어서 별도로 sdk를 설치하지 않아도 됩니다.
2. manifest 파일에서 allowBackup을 "true" 로 설정해주세요.
3. `android/build.gradle` 에 아래 내용을 추가합니다.

```gradle
allprojects {
    repositories {
        ...
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
    }
}
```

4. 안드로이드 카카오 SDK 설치 후의 설정과 관련해서는 [카카오 개발자 페이지 - 앱생성](https://developers.kakao.com/docs/latest/ko/getting-started/app)을 참고해주세요. <b>앱생성</b> 가이드를 따라하고 성공적으로 build가 되는 것을 확인하시면 아래를 진행하시면 됩니다.
5. `app/src/main/res/values/strings.xml` 을 열어 다음을 추가합니다

```diff
<resources>
    <string name="app_name">KakaoLoginExample</string>
+   <string name="kakao_app_key">your_app_key</string>
</resources>
```

5. [공식문서-토큰관리](https://developers.kakao.com/docs/latest/ko/kakaologin/android#token-mgmt) 에서 참고할 수 있듯이 Android 카카오 SDK는 액세스 토큰을 자동 갱신해줍니다.
6. 컴파일 에러가 나면 `build.gradle`에서 android sdk compile version 등 빌드 sdk 버전을 맞춰주세요.
7. 아래와 같은 에러가 발생할 경우 [키 해시 등록](https://developers.kakao.com/docs/latest/ko/getting-started/sdk-android-v1#key-hash)을 진행해주세요. 자바 코드로 구하는 방법이 제일 확실합니다.

```
AUTHORIZATION_FAILED: invalid android_key_hash or ios_bundle_id or web_site_url
```

React Native 0.60.x 부터 기본적으로 포함되는 디버깅 키의 해시는 다음과 같고 `../project/android/app`에 디버그용 키스토어가 존재합니다

ex: `Xo8WBi6jzSxKDVR4drqm84yr9iU=`

## Changelogs

[Changelogs 링크](./CHANGELOG.md)

#### Methods (callback is optional)

| Func         |                                       Param                                     |     Return      | Description                |
| :----------- | :-----------------------------------------------------------------------------: | :-------------: | :--------------------------|
| login          |     | Promise{Object} | 로그인                       |
| getProfile     |                     `callback? (err: Error, result: Object)`                      | Promise{Object} | 프로필 불러오기                |
| logout         |                     `callback? (err: Error, result: String)`                      | Promise{String} | 로그아웃                     |
| unlink         |                     `callback? (err: Error, result: String)`                      | Promise{String} | 연결끊기                     |
| getAccessToken |                     `callback? (err: Error, result: String)`                      | Promise{Object} | 액세스 토큰, 리프레시 토큰 조회   |

#### Params in result when `login` succeeded

|                         | iOS | Android |              type               |                                         Description                                         |
| ----------------------- | :-: | :-----: | :-----------------------------: | :-----------------------------------------------------------------------------------------: |
| `accessToken`           |  ✓  |    ✓    |            `string`             |                                            토큰                                             |
| `refreshToken`          |  ✓  |    ✓    |            `string`             |                                        리프레쉬 토큰                                        |
| `accessTokenExpiresAt`  |  ✓  |    ✓    |      `yyyy-MM-ddThh:mm:ss`      |                                       토큰 만료 시간                                        |
| `refreshTokenExpiresAt` |  ✓  |    ✓    | `yyyy-MM-ddThh:mm:ss` or `null` | 리프레쉬 토큰 만료 시간, 구버전 SDK로 이미 로그인이 되어있었다면 null이 반환될 수 있습니다. |
| `scopes`                |  ✓  |         |           `string[]`            |                                   사용자로 부터 받은 권한                                   |

## Usage



## How to run example project

1. `clone` 받은 레포에서 `KakaoLoginExample` 폴더로 이동합니다

```bash
  cd KakaoLoginExample
```

2. 필요한 모듈을 설치 합니다(`preinstall`이 실행됩니다)

```bash
    npm install
    #OR
    yarn install
```

3. 프로젝트 실행

- `KAKAO_APP_KEY`등 필요한 SDK 연동 설정은 기본으로 되어 있습니다
- `npm run start`
- `npm run ios` or `npm run android`로 앱 실행
- ios의 경우 `ios`폴더에서 `pod install`을 먼저 실행해 주세요 (충돌시 `lock파일` 삭제 후 설치)
