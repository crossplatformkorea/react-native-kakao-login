# @react-native-seoul/kakao-login.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))
# Newest version Kakao publishes to the CocoaPods trunk. Their iOS releases past
# 2.22.7 (2.23.x, 2.24.x) ship as Swift Package Manager tags only and have no
# podspec, so this cannot be raised to match the Android SDK version.
kakao_sdk_version = "2.22.7"

Pod::Spec.new do |s|
  s.name         = "kakao-login"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  @react-native-seoul/kakao-login
                   DESC
  s.homepage     = "https://github.com/react-native-seoul/react-native-kakao-login"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "dooboolab" => "support@dooboolab.com" }
  s.platforms    = { :ios => "13.0" }
  s.framework     = 'UIKit'
  s.source       = { :git => "https://github.com/@react-native-seoul/react-native-kakao-login.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  s.requires_arc = true

  s.dependency "React"

  if defined?($KakaoSDKVersion)
    Pod::UI.puts "#{s.name}: Using user specified Kakao SDK version '#{$KakaoSDKVersion}'"
    kakao_sdk_version = $KakaoSDKVersion
  end

  s.dependency 'KakaoSDKCommon',  kakao_sdk_version
  s.dependency 'KakaoSDKAuth',  kakao_sdk_version
  s.dependency 'KakaoSDKUser', kakao_sdk_version
end

