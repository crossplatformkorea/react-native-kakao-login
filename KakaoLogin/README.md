#### React-native-web

1.RestApiKey랑 redirectUrl을 포함한 아래 링크로 href 링크를 열어서 code를 가져옵니다
const kakaoUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

redirectUrl이 http://localhost:3000 일때 아래와같이 redirectUrl에 code파라미터가 붙은 url이 들어와집니다

http://localhost:3000/?code=Ss32OM1_yUybn5dtEQ-XT8EZfV24BKC_GIeIvFPz7_wHorYXtij9JFQcMuGtGdzxQc3Vlwopb1UAAAGCizvuCw
code= 뒤쪽부분을 split해서 토큰 발급시 필요한 code를 얻을 수 있습니다
react-native-web에서는 app과 다르게 restApikey, redirecturl을 code와 같이 직접 넣어줘야 합니다


## Methods (Web)

| Func                  | Param |            Return             | Description                                                                                                        |
| :-------------------- | :---: | :---------------------------: | :----------------------------------------------------------------------------------------------------------------- |
| login                 |   restApiKeyWeb, redirectUrlWeb, codeWeb    |   Promise{KakaoOAuthWebToken} | 로그인                                                    |
| loginWithKakaoAccount |       |      | 웹 지원 x |
| getProfile            |    tokenWeb   |     Promise{KakaoProfile}     | 프로필 불러오기                                                                                                    |
| logout                |    tokenWeb   |        Promise{string}        | 로그아웃                                                                                                           |
| unlink                |   tokenWeb    |        Promise{string}        | 연결끊기                                                                                                           |
| getAccessToken        |       |  | 웹 지원 x  
