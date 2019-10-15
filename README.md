# @react-native-seoul/kakao-login

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
React Native 카카오 로그인 라이브러리 입니다.
세부 예제는 KakaoLoginExample 폴더 안의 예제 프로젝트를 확인해주세요.
해당 라이브러리는 `flow`와 `typescript`를 지원합니다.

## Getting started

- RN >= 0.60 (최신 버전)

```shell
$ npm install @react-native-seoul/kakao-login
# OR
$ yarn add @react-native-seoul/kakao-login
```

- RN < 0.60 (1.3.8 버전 이하로 설치 해주세요)

```shell
$ npm install react-native-kakao-logins@1.3.8
# OR
$ yarn add react-native-kakao-login@s1.3.8
```

### Automatic installation

##### RN >= 0.60

React Native 0.60.X이상부터는 `Auto linking`을 지원합니다. 수동으로 설치를 원하시면 아래의 `Manual installation`를 참조하세요

##### RN < 0.60

`$ react-native link @react-native-seoul/kakao-login` 또는 아래의 `Manual installation`를 참조하세요

### Manual installation

##### [RN >= 0.60] Manual installation을 원한다면 다음 설정을 먼저 해주세요

`react-native.config.js` 파일을 root디렉토리에 생성하고 다음처럼 설정합니다

```js
// react-native.config.js
module.exports = {
  dependencies: {
    '@react-native-seoul/kakao-login': {
      // Set null on platform that you want manual installation
      platforms: {
        ios: null,
        android: null,
      },
    },
  },
};
```

#### iOS

1.`cocoapods` 또는 카카오 홈페이지 Download 를 통한 iOS KakaoOpenSDK 설치

- `cocoapods` 을 통한 설치
  `ios/Podfile`에 다음처럼 추가 해주세요

  ```ruby
  target 'yourApp' do
      ...
      pod 'KakaoOpenSDK', '~> 1.16.0' # append this line
  end
  ```

- 카카오 홈페이지 Download 를 통한 설치
  - [카카오 홈페이지 다운로드 링크](https://developers.kakao.com/docs/ios/getting-started#kakao-sdk-설치)
  - <b>앱생성 가이드 전</b> 까지 가이드대로 `SDK Framework`를 프로젝트에 import 해주세요

2. Xcode를 열고, project main navigator, `Libraries` 폴더 우클릭 ➜ `Add Files to [your project's name]` 클릭
3. 프로젝트의 `node_modules` 폴더 ➜ `@react-native-seoul/kakao-login` 그리고 `RNKakaoLogins.xcodeproj` 를 `Libraries`폴더에 추가합니다
4. Xcode navigator에서 원하는 Target을 선택하고, `libRNKakaoLogins.a`파일을 `Build Phases` ➜ `Link Binary With Libraries`에 추가합니다
5. `Post installation`를 참고하세요

#### Android

1. `android/app/src/main/java/[...]/MainApplication.java` 파일을 열고 다음처럼 추가합니다

   ```diff
      import com.facebook.soloader.SoLoader;
   +  import com.dooboolab.kakaologins.RNKakaoLoginsPackage;
   ```

   - RN < 0.60

   ```diff
    @Override
      protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
   +        new RNKakaoLoginsPackage()
        );
      }
   ```

   - RN >= 0.60

   ```diff
   @Override
     protected List<ReactPackage> getPackages() {
       @SuppressWarnings("UnnecessaryLocalVariable")
       List<ReactPackage> packages = new PackageList(this).getPackages();
       // Packages that cannot be autolinked yet can be added manually here, for example:
       // packages.add(new MyReactNativePackage());
   +      packages.add(new RNKakaoLoginsPackage());
       return packages;
   }
   ```

2) `android/settings.gradle`에 다음을 추가합니다:
   ```diff
   +  include ':@react-native-seoul/kakao-login'
   +  project(':@react-native-seoul/kakao-login').projectDir = new File(rootProject.projectDir,     '../node_modules/@react-native-seoul/kakao-login/android')
   ```
3) `android/app/build.gradle`의 dependencies block 에 다음을 추가합니다:
   ```diff
     implementation "com.facebook.react:react-native:+"  // From node_modules
   + implementation project(':@react-native-seoul/kakao-login')
   ```

### Post installation

#### iOS

1. 프로젝트의 ios 폴더에서 `$ pod install` 명령어를 실행합니다. (KaKao SDK를 직접 다운받아서 설치한 경우는 제외)
2. ios 카카오 sdk 설치 후의 설정과 관련해서는 [공식문서 - 앱생성](https://developers.kakao.com/docs/ios/getting-started#앱-생성) 을 참고해주세요. <b>앱생성</b> 가이드를 따라하고 성공적으로 build가 되는 것을 확인하시면 아래를 진행하시면 됩니다.
3. Project => Targets 아래 앱 선택 => General 탭으로 이동해서 Bundle Identifier가 본인의 카카오 앱과 동일한지 확인해주세요.
4. [공식문서 - 개발 프로젝트 설정](https://developers.kakao.com/docs/ios/getting-started#%EA%B0%9C%EB%B0%9C%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%A4%EC%A0%95) 을 참고하여 `info.plist`, `URL Types` 및 커스텀 스킴 추가 등 기타 필요한 세팅들을 프로젝트에 추가해줍니다.
5. [공식문서 - 로그인](https://developers.kakao.com/docs/ios/user-management#%EB%A1%9C%EA%B7%B8%EC%9D%B8) 을 참고하여 `AppDelegate.m` 파일에 아래와 같은 내용을 추가합니다.

   ```
   #import <KakaoOpenSDK/KakaoOpenSDK.h>

   - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
                                         sourceApplication:(NSString *)sourceApplication
                                                 annotation:(id)annotation {
       if ([KOSession isKakaoAccountLoginCallback:url]) {
           return [KOSession handleOpenURL:url];
       }

       return false;
   }

   - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url
                                                   options:(NSDictionary<NSString *,id> *)options {
       if ([KOSession isKakaoAccountLoginCallback:url]) {
           return [KOSession handleOpenURL:url];
       }

       return false;
   }

   - (void)applicationDidBecomeActive:(UIApplication *)application
   {
       [KOSession handleDidBecomeActive];
   }
   ```

6. Expo Kit을 사용하여 개발하는 경우 `RNKakaoLogin.xcodeproj`의 Build Settings => Header Search Paths에 `$(SRCROOT)/../../../ios/Pods/Headers/Public`를 `recursive`로 추가해주셔야 합니다.

7. 잘 안되시면 Example Project를 확인하여 비교해보시면 되겠습니다.

#### Android

1. 안드로이드에서는 카카오 SDK가 모듈의 gradle 경로에 잡혀있어서 별도로 sdk를 설치하지 않아도 됩니다.
2. manifest 파일에서 allowBackup을 "true" 로 설정해주세요.
3. `android/build.gradle` 에 아래 내용을 추가합니다.

```gradle
subprojects {
    repositories {
        ...
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/ +nexus/content/groups/public/' }
    }
}
```

4. 안드로이드 카카오 SDK 설치 후의 설정과 관련해서는 [카카오 개발자 페이지 - 앱생성](https://developers.kakao.com/docs/android/getting-started#앱-생성)을 참고해주세요. <b>앱생성</b> 가이드를 따라하고 성공적으로 build가 되는 것을 확인하시면 아래를 진행하시면 됩니다.
5. `app/src/main/res/values/strings.xml` 을 열어 다음을 추가합니다

```diff
<resources>
    <string name="app_name">KakaoLoginExample</string>
+   <string name="kakao_app_key">your_app_key</string>
</resources>
```

6. 컴파일 에러가 나면 `build.gradle`에서 android sdk compile version 등 빌드 sdk 버전을 맞춰주세요.
7. 아래와 같은 에러가 발생할 경우 [키 해시 등록](https://developers.kakao.com/docs/android/getting-started#키해시-등록)을 진행해주세요. 자바 코드로 구하는 방법이 제일 확실합니다.

```
AUTHORIZATION_FAILED: invalid android_key_hash or ios_bundle_id or web_site_url
```

React Native 0.60.x 부터 기본적으로 포함되는 디버깅 키의 해시는 다음과 같고 `../project/android/app`에 디버그용 키스토어가 존재합니다

ex: `Xo8WBi6jzSxKDVR4drqm84yr9iU=`

## Changelogs

[Changelogs 링크](https://github.com/react-native-seoul/react-native-kakao-login/blob/master/CHANGELOG.md)

#### Methods

| Func       | Param |                  Return                  | Description      |
| :--------- | :---: | :--------------------------------------: | :--------------- |
| login      |       | `callback (err: string, result: object)` | 로그인.          |
| getProfile |       | `callback (err: string, result: object)` | 프로필 불러오기. |
| logout     |       |  `callback (err: string, result: null)`  | 로그아웃.        |

#### params in result when `getProfile`

- version > 1.3.8

|                     | iOS | Android |    Description     |
| ------------------- | :-: | :-----: | :----------------: |
| `id`                |  ✓  |    ✓    | 카카오 고유 아이디 |
| `nickname`          |  ✓  |    ✓    |        별칭        |
| `profile_image_url` |  ✓  |    ✓    |   프로필 이미지    |
| `thumb_image_url`   |  ✓  |    ✓    |   썸네일 이미지    |
| `email`             |  ✓  |    ✓    |    이메일 주소     |
| `display_id`        |  ✓  |    ✓    |      별칭 id       |
| `phone_number`      |  ✓  |    ✓    |    휴대폰 번호     |
| `is_email_verified` |  ✓  |    ✓    |  이메일 인증 여부  |
| `is_kakaotalk_user` |  ✓  |    ✓    | 카카오톡 유저 여부 |
| `has_signed_up`     |  ✓  |    ✓    |     가입 여부      |

`email` / `phone_number` / `display_id` / `is_email_verified` / `is_kakaotalk_user` / `has_signed_up` <strong>해당 값들은 사용자의 동의 혹은 제휴를 통해 권한이 부여된 특정 앱에서만 획득할 수 있습니다. 권한이 있다면 그에 맞는 값을 리턴하고, 권한이 없다면 null 값을 반환합니다.</strong>

- version <= 1.3.8

|                      | iOS | Android |      Comment       |
| -------------------- | :-: | :-----: | :----------------: |
| `id`                 |  ✓  |    ✓    | 카카오 고유 아이디 |
| `nickname`           |  ✓  |    ✓    |        별칭        |
| `email`              |  ✓  |    ✓    |    이메일 주소     |
| `display_id`         |     |    ✓    |      별칭 id       |
| `phone_number`       |     |    ✓    |    휴대폰 번호     |
| `email_verified`     |  ✓  |    ✓    |  이메일 인증 여부  |
| `kakaotalk_user`     |     |    ✓    | 카카오톡 유저 여부 |
| `profile_image_path` |  ✓  |    ✓    |   프로필 이미지    |
| `thumb_image_path`   |  ✓  |    ✓    |   썸네일 이미지    |
| `has_signed_up`      |     |    ✓    |     가입 여부      |

4가지 `attribute` 대해 아직 ios에서 아직 어떻게 받는지 확인이 안되어 android와 상이한 부분이 있습니다.

## Usage

[@react-native-seoul/kakao-login/KakaoLoginExample/App.js](https://github.com/react-native-seoul/react-native-kakao-login/blob/master/KakaoLoginExample/App.js)

로그인 후 result에 들어오는 결과값은 `{token:kakao_token}`입니다.
