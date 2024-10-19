import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppfueledTwilioCapacitorPlugin)
public class AppfueledTwilioCapacitorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppfueledTwilioCapacitorPlugin"
    public let jsName = "AppfueledTwilioCapacitor"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = AppfueledTwilioCapacitor()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
