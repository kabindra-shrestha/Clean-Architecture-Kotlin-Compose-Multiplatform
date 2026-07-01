package org.dany.worker

import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker

actual fun createSqlJsWorker() =
    WebWorkerSQLiteDriver(Worker(js("""new URL("sql-js-worker/worker.js", import.meta.url)""")))