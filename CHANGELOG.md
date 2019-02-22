## Changelogs
- **[1.3.0]**
  + Upgrade android kakaosdk to 1.15.1.
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