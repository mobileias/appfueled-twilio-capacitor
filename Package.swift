// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "AppfueledTwilioCapacitor",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "AppfueledTwilioCapacitor",
            targets: ["AppfueledTwilioCapacitorPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "AppfueledTwilioCapacitorPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AppfueledTwilioCapacitorPlugin"),
        .testTarget(
            name: "AppfueledTwilioCapacitorPluginTests",
            dependencies: ["AppfueledTwilioCapacitorPlugin"],
            path: "ios/Tests/AppfueledTwilioCapacitorPluginTests")
    ]
)