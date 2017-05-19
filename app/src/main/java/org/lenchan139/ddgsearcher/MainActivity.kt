package org.lenchan139.ddgsearcher

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.EditText
import org.lenchan139.ddgsearcher.Class.WebViewOverride
import android.view.KeyEvent.KEYCODE_ENTER
import org.droidparts.widget.ClearableEditText
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    var webView: WebViewOverride? = null
    var edtSearcher : ClearableEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        webView = findViewById(R.id.webView) as WebViewOverride
        edtSearcher = findViewById(R.id.search_bar) as ClearableEditText
        webView!!.getSettings().setJavaScriptEnabled(true);
        webView!!.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            searchDdg(edtSearcher!!.text.toString());
        }
        edtSearcher!!.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                //To change body of created functions use File | Settings | File Templates.
                if (event?.getAction() === KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Perform action on key press
                    searchDdg(edtSearcher!!.text.toString())
                    return true
                }
                return false
            }
        })
        fab.visibility = View.GONE

    }

    override fun onResume() {

        if(Intent.ACTION_ASSIST == intent.action) {
            edtSearcher!!.requestFocus()
            edtSearcher!!.performClick()
            edtSearcher!!.selectAll()
        }

        super.onResume()
    }

    override fun onBackPressed() {
        val intent = Intent(this, OpenFromAssistActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    fun openExternal(u : String){

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(u))
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "No Handler here.", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
fun searchDdg(keyword: String){
    var url = "https://duckduckgo.com/?ko=-1&q=" + keyword
    if(webView!!.loadUrlWithChecker(url)){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
    hideKeybord()
    webView!!.requestFocus()
}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun hideKeybord() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
