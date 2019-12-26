require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "kakao-login"
  s.version      = package['version']
  s.summary      = "Kakao Login Module for React Native (iOS and Android)"

  s.authors      = { "author" => "dooboolab@gmail.com" }
  s.homepage     = "https://github.com/react-native-seoul/kakao-login"
  s.license      = package['license']
  s.platform     = :ios, "9.0"

  s.source       = { :git => "https://github.com/react-native-seoul/kakao-login.git", :tag => "#{s.version}" }
  s.source_files = "ios/**/*.{h,m}"

  s.static_framework = true

  s.dependency 'React'
  s.dependency 'KakaoOpenSDK', '~> 1.20.0'
end

