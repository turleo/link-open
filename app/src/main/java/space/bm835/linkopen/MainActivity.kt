package space.bm835.linkopen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection


class MainActivity : AppCompatActivity() {
    var urlText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = intent.data

        val share: Button = findViewById(R.id.button3)
        val open: Button = findViewById(R.id.button)
        val unshort: Button = findViewById(R.id.unshortButton)
        urlText = findViewById(R.id.textView2)

        var url = ""
        if (uri != null) {
            url = uri.toString()
        }

        val out = findViewById<TextView>(R.id.textView2)
        out.text = url

        share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, url)
            intent.type = "text/plain"
            startActivity(intent)
        }

        open.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        unshort.setOnClickListener {
            if (url.startsWith("https://") || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
                val task = RequestTask()
                task.execute(RequestTaskParams(url, this))
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.http_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class RequestTaskParams internal constructor(
        var url: String,
        var activity: Activity?
    )

    internal class RequestTask :
        AsyncTask<RequestTaskParams?, Void?, RequestTaskParams?>() {


        override fun onPostExecute(result: RequestTaskParams?) {
            super.onPostExecute(result)
            result?.activity?.findViewById<TextView>(R.id.textView2)?.text = result?.url
        }

        override fun doInBackground(vararg params: RequestTaskParams?): RequestTaskParams? {
            var uri = params[0]?.url
            try {
                val ipAddress = URL(uri)
                val urlConnection: URLConnection = ipAddress.openConnection()
                val bufferedReader = BufferedReader(
                    InputStreamReader(
                        urlConnection.getInputStream()
                    )
                )
                uri = urlConnection.url.toString()
                bufferedReader.close()
            } catch (e: Exception) {
                return RequestTaskParams(e.localizedMessage, params[0]?.activity)
            }
            return RequestTaskParams(uri, params[0]?.activity)
        }
    }
}
