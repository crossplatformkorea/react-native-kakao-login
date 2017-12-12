
Pod::Spec.new do |s|
  s.name         = "RNKakaoLogins"
  s.version      = "1.0.0"
  s.summary      = "RNKakaoLogins"
  s.description  = <<-DESC
                  RNKakaoLogins
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNKakaoLogins.git", :tag => "master" }
  s.source_files  = "RNKakaoLogins/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  