package works.wever.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import wever.works.datastore.Settings
import works.wever.datastore.storage.SettingsDataStore
import works.wever.datastore.ui.DataStoreTheme

class MainActivity : AppCompatActivity() {

    val settingsDataStore by lazy { SettingsDataStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentNumber = settingsDataStore.data
                .collectAsState(Settings.getDefaultInstance())

            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        horizontalGravity = CenterHorizontally,
                        verticalArrangement = SpaceEvenly,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(onClick = { updateData(1) }) {
                            Text(text = "Update to 1")
                        }
                        Button(onClick = { updateData(2) }) {
                            Text(text = "Update to 2")
                        }
                        Button(onClick = { updateData(3) }) {
                            Text(text = "Update to 3")
                        }
                        Button(onClick = { updateData(4) }) {
                            Text(text = "Update to 4")
                        }

                        Text(text = "Current number is: ${currentNumber.value.exampleCounter}")
                    }
                }
            }
        }
    }

    private fun updateData(newNumber: Int) {
        lifecycleScope.launch {
            settingsDataStore.updateData { settings ->
                settings.toBuilder().setExampleCounter(newNumber).build()
            }
        }
    }
}