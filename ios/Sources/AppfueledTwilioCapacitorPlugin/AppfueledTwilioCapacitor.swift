import Foundation

@objc public class AppfueledTwilioCapacitor: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
