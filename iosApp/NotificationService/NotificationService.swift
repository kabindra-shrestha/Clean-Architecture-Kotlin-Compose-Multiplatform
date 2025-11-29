//
//  NotificationService.swift
//  NotificationService
//
//  Created by Kabindra Shrestha on 24/12/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UserNotifications

class NotificationService: UNNotificationServiceExtension {

    var contentHandler: ((UNNotificationContent) -> Void)?
    var bestAttemptContent: UNMutableNotificationContent?

    override func didReceive(_ request: UNNotificationRequest, withContentHandler contentHandler: @escaping (UNNotificationContent) -> Void) {
        self.contentHandler = contentHandler
        bestAttemptContent = (request.content.mutableCopy() as? UNMutableNotificationContent)

        if let bestAttemptContent = bestAttemptContent {
            // Modify the notification content here...

            let userInfo: NotificationPayload? = ((request.content.userInfo as NSDictionary) as? Dictionary<String, Any>)?.object()

            if userInfo == nil {
                contentHandler(bestAttemptContent)
                return
            }

            guard let url = URL(string: userInfo?.fcmOptions?.image ?? "") else {
                contentHandler(bestAttemptContent)
                return
            }

            guard let data = try? Data(contentsOf: url) else {
                contentHandler(bestAttemptContent)
                return
            }

            let identifier = "file.\(url.pathExtension)"

            guard let attachment = UNNotificationAttachment.saveImageToDisk(fileIdentifier: identifier, data: data as NSData, options: nil) else {
                contentHandler(bestAttemptContent)
                return
            }

            bestAttemptContent.attachments = [attachment]
            contentHandler(bestAttemptContent)
        }

    }

    override func serviceExtensionTimeWillExpire() {
        // Called just before the extension will be terminated by the system.
        // Use this as an opportunity to deliver your "best attempt" at modified content, otherwise the original push payload will be used.
        if let contentHandler = contentHandler, let bestAttemptContent = bestAttemptContent {
            contentHandler(bestAttemptContent)
        }
    }
}

@available(iOSApplicationExtension 10.0, *)
extension UNNotificationAttachment {

    static func saveImageToDisk(fileIdentifier: String, data: NSData, options: [NSObject: AnyObject]?) -> UNNotificationAttachment? {
        let fileManager = FileManager.default
        let folderName = ProcessInfo.processInfo.globallyUniqueString
        let folderURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent(folderName, isDirectory: true)

        do {
            try fileManager.createDirectory(at: folderURL!, withIntermediateDirectories: true, attributes: nil)
            let fileURL = folderURL?.appendingPathComponent(fileIdentifier)
            try data.write(to: fileURL!, options: [])
            let attachment = try UNNotificationAttachment(identifier: fileIdentifier, url: fileURL!, options: options)
            return attachment
        } catch let error {
            debugPrint("error \(error)")
        }

        return nil
    }
}
