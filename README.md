# @react-native-seoul/kakao-login

<img src="image/main_banner.png" width="100%" height="auto" />

[![VERSION](http://img.shields.io/npm/v/@react-native-seoul/kakao-login.svg?style=flat-square)](https://npmjs.org/package/@react-native-seoul/kakao-login)
[![DOWNLOAD](http://img.shields.io/npm/dm/@react-native-seoul/kakao-login.svg?style=flat-square)](https://npmjs.org/package/@react-native-seoul/kakao-login)
[![LICENSE](http://img.shields.io/npm/l/@react-native-seoul/kakao-login.svg?style=flat-square)](https://npmjs.org/package/@react-native-seoul/kakao-login)
[![CI](https://github.com/react-native-seoul/react-native-kakao-login/actions/workflows/ci.yml/badge.svg)](https://github.com/react-native-seoul/react-native-kakao-login/actions/workflows/ci.yml)
[![publish-package](https://github.com/react-native-seoul/react-native-kakao-login/actions/workflows/ci-deploy.yml/badge.svg)](https://github.com/react-native-seoul/react-native-kakao-login/actions/workflows/ci-deploy.yml)

React Native 카카오 로그인 라이브러리 입니다. `@react-native-seoul/kakao-login` < 3.0 이하 버전을 쓰시는 분들은 [DEPRECATED README](https://github.com/react-native-seoul/react-native-kakao-login/blob/master/README_DEPRECATED.md)를 참고해주세요.

세부 예제는 KakaoLoginExample 폴더 안의 예제 프로젝트를 확인해주세요.
해당 라이브러리는 `flow`와 `typescript`를 지원합니다.

## Changelogs

[Changelogs 링크](./CHANGELOG.md)

## Demo

[카카오 로그인 Example Project](https://github.com/react-native-seoul/react-native-kakao-login/tree/master/KakaoLoginExample) 데모 화면

<img src="https://user-images.githubusercontent.com/27461460/111674724-a7989f00-885f-11eb-9e51-d146757ca836.gif" width="200">

> 위 프로젝트는 `KakaoLoginExample` 폴더에서 확인 가능합니다.

## Tutorial

> 라이브러리르 더욱 편리하게 사용하기 위해서 Youtube 영상을 제작했습니다.

* [iOS에서 사용하기 Youtube](https://www.youtube.com/watch?v=uCn1xIijuos&list=PLMu8UG37vF6oJLNhjsjoy_ApcJFZZwJOo)

* [Android에서 사용하기 Youtube](https://www.youtube.com/watch?v=YJaOT3ZVKNg&list=PLMu8UG37vF6oJLNhjsjoy_ApcJFZZwJOo)

## Getting started

해당 라이브러리는 `3.0.0` 이후 부터는 react-native `0.61`이상을 지원합니다. 카카오 라이브러리 지원이 아래 버전부터는 지원이 끊길 예정이므로 참고해주시기 바랍니다. 과거에는 [카카오 라이브러리 레거시 iOS](https://developers.kakao.com/docs/latest/ko/kakaologin/ios-v1)와 [카카오 라이브러리 레거시 Android](https://developers.kakao.com/docs/latest/ko/kakaologin/android-v1) 버전을 쓰고 있었습니다.

## Installation

```
yarn add @react-native-seoul/kakao-login
```

React Native 0.60.X이상부터는 `Auto linking`을 지원합니다. 따라서 설치는 매우 간편합니다.

iOS의 경우 `yarn add @react-native-seoul/kakao-login` 이후 `npx pod-install` 명령어로 pod 라이브러리만 추가로 설치해주시면 됩니다.

## Post Installation
> 설치가 제대로 되지 않는다면 example project의 설정을 참고하세요 👍

#### iOS

1. Pod에서 iOS deployment target이 `11.0` 이상이어야 합니다.

2. ios 카카오 sdk 설치 후의 설정과 관련해서는 [공식문서 - 카카오 로그인 > 설정하기](https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite)를 참고해주세요. 해당 가이드를 통해 카카오 개발자 페이지에서 본인의 어플리케이션을 생성해주세요.

3. [공식문서 - 개발 프로젝트 설정](https://developers.kakao.com/docs/latest/ko/getting-started/sdk-ios-v1) 을 참고하여 `info.plist`, `URL Types` 및 커스텀 스킴 추가 등 기타 필요한 세팅들을 프로젝트에 추가해줍니다. 아래`카카오 네이티브앱 아이디를 적어주세요` 문구를 잘 확인하시여 본인의 Kakao App Key로 변경해주세요.
   ```diff
    <key>CFBundleURLTypes</key>
    <array>
   + <dict>
   +   <key>CFBundleTypeRole</key>
   +   <string>Editor</string>
   +   <key>CFBundleURLSchemes</key>
   +   <array>
   +     <string>kakao{카카오 네이티브앱 아이디를 적어주세요}</string>
   +   </array>
   + </dict>
    </array>
    <key>CFBundleVersion</key>
    <string>1</string>
   + <key>KAKAO_APP_KEY</key>
   + <string>{카카오 네이티브앱 아이디를 적어주세요}</string>
   + <key>LSApplicationQueriesSchemes</key>
   + <array>
   +   <string>kakaokompassauth</string>
   +   <string>storykompassauth</string>
   +   <string>kakaolink</string>
   + </array>
   ```

4. `3.0.0` 버전부터는 swift 버전의 kakao sdk를 활용하므로 [Swift Bridging Header](https://stackoverflow.com/questions/31716413/xcode-not-automatically-creating-bridging-header)를 추가해야할 수 있습니다.
   <img width="800" alt="1" src="https://user-images.githubusercontent.com/27461460/111863065-8be6e300-899c-11eb-8ad8-6811e0bd0fbd.png">

5. `AppDelegate.m` 파일에 [해당 부분](https://github.com/react-native-seoul/react-native-kakao-login/issues/193#issuecomment-806475082)을 추가해주세요. 이는 카카오톡 앱이 깔려 있을시 올바로 데이터를 받아오기 위함입니다 [#193](https://github.com/react-native-seoul/react-native-kakao-login/issues/193).

6. Project => Targets 아래 앱 선택 => General 탭으로 이동해서 Bundle Identifier가 본인의 카카오 앱과 동일한지 확인해주세요.

#### Android

1. [키 해시 등록](https://developers.kakao.com/docs/latest/ko/getting-started/sdk-android-v1#key-hash)을 진행해주세요. 자바 코드로 구하는 방법이 제일 확실합니다.

   ```
   AUTHORIZATION_FAILED: invalid android_key_hash or ios_bundle_id or web_site_url
   ```

   React Native 0.60.x 부터 기본적으로 포함되는 디버깅 키의 해시는 다음과 같고 `../project/android/app`에 디버그용 키스토어가 존재합니다

   ex: `Xo8WBi6jzSxKDVR4drqm84yr9iU=`

   * React Native에서는 개발시 `android/app/debug.keystore`의 해시를 추가해주시면 됩니다.
     ```
     keytool -exportcert -alias androiddebugkey -keystore ~./android/app/debug.keystore -storepass android -keypass android | openssl sha1 -binary | openssl base64
     ```

2. 안드로이드에서는 카카오 SDK가 모듈의 gradle 경로에 잡혀있어서 별도로 sdk를 설치하지 않아도 됩니다.  가끔 `kakao sdk`를 못찾겠다는 오류가 나오면 `build.gradle(Project)` 파일에 다음과 같이 android sdk repository를 추가해주세요.
   ```
   maven { url 'https://devrepo.kakao.com/nexus/content/groups/public/' }
   ```

3. Manifest 파일에서 allowBackup을 `true`로 변경해주세요.
   ```diff
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.kakaologinexample"
   >
    <uses-permission android:name="android.permission.INTERNET" />

    <application
   +  android:allowBackup="true"
   ```

4. Redirect URI 설정
   * 카카오 로그인 기능을 구현하기 위해서는 리다이렉션(Redirection)을 통해 [Request Code](https://developers.kakao.com/docs/latest/ko/kakaologin/android)를 받아야 합니다. 그러기 위해서는 아래 코드를 `AndroidManifest.xml`에 추가해주세요. 그리고 `카카오 네이티브 앱 key를 입력해주세요` 텍스트를 본인의 카카오 네이티브 키로 변경해주시면 됩니다. 
     ```
     <activity android:name="com.kakao.sdk.auth.AuthCodeHandlerActivity">
       <intent-filter>
           <action android:name="android.intent.action.VIEW" />
           <category android:name="android.intent.category.DEFAULT" />
           <category android:name="android.intent.category.BROWSABLE" />

           <!-- Redirect URI: "kakao{NATIVE_APP_KEY}://oauth“ -->
           <data android:host="oauth"
               android:scheme="kakao{카카오 네이티브 앱 key를 입력해주세요}" />
       </intent-filter>
     </activity>
     ```

5. `app/src/main/res/values/strings.xml` 을 열어 다음을 추가합니다
    ```diff
    <resources>
        <string name="app_name">KakaoLoginExample</string>
    +   <string name="kakao_app_key">your_app_key</string>
    </resources>
    ```

6. kotlin을 프로젝트에서 해석가능하도록 설치해줍니다. `android/build.gradle` 파일에 아래 변경사항을 적용해주세요.
   ```diff
   buildscript {
     ext {
         buildToolsVersion = "29.0.3"
         minSdkVersion = 21
         compileSdkVersion = 29
         targetSdkVersion = 29
   +     kotlinVersion = '1.3.41'

         ndkVersion = "20.1.5948944"
     }
     repositories {
         google()
         jcenter()
     }
     dependencies {
         classpath("com.android.tools.build:gradle:4.1.0")
   +     classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
     }
   ...
   ```

7. [공식문서-토큰관리](https://developers.kakao.com/docs/latest/ko/kakaologin/android#token-mgmt) 에서 참고할 수 있듯이 Android 카카오 SDK는 액세스 토큰을 자동 갱신해줍니다.

8. 컴파일 에러가 나면 `build.gradle`에서 android sdk compile version 등 빌드 sdk 버전을 맞춰주세요.

## Methods

| Func         |                                       Param                                     |     Return      | Description                |
| :----------- | :-----------------------------------------------------------------------------: | :-------------: | :--------------------------|
| login      |    | Promise{KakaoOAuthToken} | 로그인 |
| getProfile |    | Promise{KakaoProfile} | 프로필 불러오기 |
| logout     |    | Promise{string} | 로그아웃 |
| unlink     |    | Promise{string} | 연결끊기 |
| getAccessToken || Promise{KakaoAccessTokenInfo} | 액세스 토큰 조회 |

#### 프로필 가져오기 - `getProfile` => `KakaoProfile`

|                         | iOS | Android | type    | Description |
| ----------------------- | :-: | :-----: | :-----: | :---------: |
| `accessToken`           |  ✓  |    ✓    | `string`   | 토큰 |
| `refreshToken?`         |  ✓  |    ✓    | `string`   | 리프레쉬 토큰  |
| `accessTokenExpiresAt?` |  ✓  |    ✓    | `Date`     | 토큰 만료 시간 |
| `refreshTokenExpiresAt?`|  ✓  |    ✓    | `Date`     | 리프레쉬 토큰 만료 시간, 구버전 SDK로 이미 로그인이 되어있었다면 null이 반환될 수 있습니다. |
| `scopes`                |  ✓  |    ✓    | `string[]` | 사용자로 부터 받은 권한 |

## Usage

### Sample Code

```js
const signInWithKakao = async (): Promise<void> => {
  const token: KakaoOAuthToken = await login();

  setResult(JSON.stringify(token));
};

const signOutWithKakao = async (): Promise<void> => {
  const message = await logout();

  setResult(message);
};

const getProfile = async (): Promise<void> => {
  const profile: KakaoProfile = await getKakaoProfile();

  setResult(JSON.stringify(profile));
};

const unlinkKakao = async (): Promise<void> => {
  const message = await unlink();

  setResult(message);
};
```

### How to run example project

1. `clone` 받은 레포에서 `KakaoLoginExample` 폴더로 이동합니다

   ```bash
   cd KakaoLoginExample
   ```

2. 필요한 모듈을 설치 합니다(`preinstall`이 실행됩니다)

   ```bash
   yarn
   ```

3. 프로젝트 실행

- `KAKAO_APP_KEY`등 필요한 SDK 연동 설정은 기본으로 되어 있습니다.
  * 본인 앱의 키로 변경하고 테스트 하셔도 무방합니다. 단 `PR`을 날리실 때는 삭제하고 날려주세요.
- `yarn start`
- `yarn ios` or `yarn android`로 앱 실행
  * `iOS` 앱이 실행되지 않을 때는 `XCode`를 열고 테스트 해주세요. 이는 RN `0.64.0`에서 발생되고 있는 문제입니다.
- ios의 경우 `ios`폴더에서 `pod install`을 먼저 실행해 주세요. 프로젝트 폴더에서 `npx pod-install`로 이용하셔도 무방합니다.
