require 'json'

package = JSON.parse(File.read(File.join(__dir__,'../package.json')))

Pod::Spec.new do |s|
  s.name         = "RNKakaoLogins"
  s.version      = package['version']
  s.summary      = package['description']
  
  s.authors      = package['author']
  s.homepage     = "https://github.com/react-native-seoul/react-native-kakao-logins"
  s.license      = package['license']
  s.platform     = :ios, "8.0"

  s.source       = { :git => "https://github.com/react-native-seoul/react-native-kakao-logins.git", :tag => "#{s.version}" }
  s.source_files  = "./*.{h,m}"
  s.requires_arc = true
  s.dependency 'React'
end