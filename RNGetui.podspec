require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |spec|
  spec.name         = "RNGetui"
  spec.summary      = package["description"]
  spec.version      = package['version']

  spec.authors      = package["author"]
  spec.homepage     = package["homepage"]
  spec.license      = "MIT"
  spec.platform     = :ios, "8.0"

  spec.source       = { :git => "https://github.com/lomocoin/react-native-getui.git" }
  spec.source_files = "ios/**/**/*.{h,m}"

  spec.dependency   "React"
end
