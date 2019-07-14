package space.bm835.linkopen

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = intent.data

        val share: Button = findViewById(R.id.button3)
        val open: Button = findViewById(R.id.button)

        var url = ""
        if (uri != null) {
            url = uri.toString()
        }

        val out = findViewById<TextView>(R.id.textView2)
        out.text = url

        share.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, url)
            intent.type = "text/plain"
            startActivity(intent)
        })

        open.setOnClickListener(View.OnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        })
    }
}
