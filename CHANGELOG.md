## Changelogs
- **[1.3.7]**
  + Upgrade android kakaosdk to 1.15.1.
  + Fix security issue [#37](https://github.com/react-native-seoul/react-native-kakao-logins/issues/37)
  + Update gradle setup [#36](https://github.com/react-native-seoul/react-native-kakao-logins/pull/36/commits/e3747ef9fe7d546d882bc12bf34d05b5babf7baf)
  + Safe null check for KakaoAccount [#40](https://github.com/react-native-seoul/react-native-kakao-logins/pull/40)
  + Better error handling in `example` [#41](https://github.com/react-native-seoul/react-native-kakao-logins/pull/41)
  + Add header seaerch path for pod project [#49](https://github.com/react-native-seoul/react-native-kakao-logins/pull/49)
- **[1.2.1]**
  + Make sure to check whether KakaoSDK already has an adapter.
- **[1.2.0]**
  + Changed the way to import Kakao SDK. Do not need to extend `GlobalApplication`.
- **[1.1.0]**
  + Prevent version miss match problem with other libraries when conflict exists
- **[1.0.3]**
  + Fixed crashing when going back canceling login
- **[1.0.2]**
  + Edit to use new methods and properly return result
  + Edit to parse native callback result
  + Edit to return values in proper json types
  Export interfaces for users to use in their own codes
- **[1.0.1]**
  + Add installation info about framework search path
  + Add steps about adding url callback for ios