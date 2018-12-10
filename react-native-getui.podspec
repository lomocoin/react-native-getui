require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |spec|
spec.name         = "react-native-getui"
spec.summary      = package["description"]
spec.version      = package['version']

spec.authors      = package["author"]
spec.homepage     = package["homepage"]
spec.license      = "MIT"
spec.platform     = :ios, "8.0"

spec.source       = { :git => "https://github.com/lomocoin/react-native-getui.git" }
spec.source_files = "ios/**/**/*.{h,m}"
# 所需的framework，多个用逗号隔开
spec.frameworks = 'SystemConfiguration', 'CFNetwork','CoreTelephony','CoreLocation','AVFoundation','Security'
# “弱引用”所需的framework，多个用逗号隔开
spec.weak_frameworks = 'UserNotifications'
# 所需的library，多个用逗号隔开
spec.libraries = 'z','sqlite3.0','c++','resolv'

spec.dependency   "React"
spec.vendored_libraries = "ios/RCTGetuiModule/RCTGetuiModule/libGTSDK.a"
end
