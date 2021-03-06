package com.android.mvvmdatabind2.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import com.android.mvvmdatabind2.R
import com.android.mvvmdatabind2.di.components.DaggerFactoryComponent
import com.android.mvvmdatabind2.di.modules.FactoryModule
import com.android.mvvmdatabind2.di.modules.RepositoryModule
import com.android.mvvmdatabind2.mvvm.repository.MainRepository
import com.android.mvvmdatabind2.mvvm.viewmodels.MainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_user_data.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_layout.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: MainViewModel
    private lateinit var component: DaggerFactoryComponent
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private val TAG = "MainActivity"
    private var currentuser: FirebaseUser? = null
    private lateinit var header: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

//        viewModel.getUsername().observe(this, { string ->
//            header.tv_email_header.text = mAuth.currentUser!!.email
//            header.tv_username_header.text = string
//        })

//        viewModel.getImage().observe(this,{
//         Picasso.get().load(it.toUri()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(header.imageView2)
//        })

        viewModel.checkUserHasData().observe(this,{
            if (it=="no")
            {
              viewModel.sendUsertoAddUserDataActivity()
            }
        })

//        header.setOnClickListener {
//            Toast.makeText(this, "Header", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun init() {
       // checkUser()
//        header = nav_Main.getHeaderView(0)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mAuth = FirebaseAuth.getInstance()
        component = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(this))
            .factoryModule(FactoryModule(MainRepository(this)))
            .build() as DaggerFactoryComponent
        viewModel = ViewModelProviders.of(this, component.getFactory())
            .get(MainViewModel::class.java)
//        setSupportActionBar(toolBar_main)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        actionBarToggle = ActionBarDrawerToggle(
//            this,
//            drawer_layout_Main,
//            toolBar_main,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )


//        actionBarToggle.syncState()

//        drawer_layout_Main.addDrawerListener(actionBarToggle)
//        actionBarToggle.syncState()
//        nav_Main.setNavigationItemSelectedListener(this)
//
//        header = nav_Main.getHeaderView(0)



    }


//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_account -> {
//                return true
//            }
//
//            R.id.menu_profile -> {
//                viewModel.sendUsertoEditProfileActivity()
//                return true
//            }
//
//            R.id.menu_logout -> {
//                viewModel.signOut()
//                finish()
//                return true
//            }
//            R.id.menu_active_membership -> {
//                return true
//            }
//            R.id.menu_set -> {
//                return true
//            }
//
//
//            R.id.menu_history -> {
//                return true
//            }
//            else -> {
//                return false
//            }
//        }
//    }


//    override fun onBackPressed() {
//        if (drawer_layout_Main.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout_Main.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }
}
