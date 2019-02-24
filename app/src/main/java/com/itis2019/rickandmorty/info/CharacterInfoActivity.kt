package com.itis2019.rickandmorty.info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.itis2019.rickandmorty.R
import com.itis2019.rickandmorty.model.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info.*

class CharacterInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar?.hide()

        val characterItem = intent.getParcelableExtra<Character>("Character item")
        bindData(characterItem)
    }

    private fun bindData(character: Character) =
        with(character) {
            Picasso.get().load(image).into(image_character)
            image_character.transitionName = intent.getStringExtra("image")
            tv_name.text = getString(R.string.name, name)
            tv_status.text = getString(R.string.status, status)
            tv_species.text = getString(R.string.species, species)
            tv_gender.text = getString(R.string.gender, gender)
            tv_origin.text = getString(R.string.origin, origin.name)
            tv_location.text = getString(R.string.location, location.name)
        }
}
