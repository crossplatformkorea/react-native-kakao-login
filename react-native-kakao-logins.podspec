require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "react-native-kakao-logins"
  s.version      = package['version']
  s.summary      = "Kakao Login Module for React Native (iOS and Android)"

  s.authors      = { "author" => "dooboolab@gmail.com" }
  s.homepage     = "https://github.com/react-native-seoul/react-native-kakao-logins"
  s.license      = package['license']
  s.platform     = :ios, "9.0"

  s.source       = { :git => "https://github.com/react-native-seoul/react-native-kakao-logins.git", :tag => "#{s.version}" }
  s.source_files  = "ios/**/*.{h,m}"
  
  s.dependency 'React'
  s.dependency 'KakaoOpenSDK', '~> 1.8.2'
end
