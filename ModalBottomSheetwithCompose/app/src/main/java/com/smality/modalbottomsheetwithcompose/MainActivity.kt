package com.smality.modalbottomsheetwithcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.smality.modalbottomsheetwithcompose.ui.theme.ModalBottomSheetWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModalBottomSheetWithComposeTheme {
                Surface(
                    //Ana ekranın bacground rengini açık mor yapalım
                    modifier = Modifier.fillMaxSize(),
                    color =Color(0xFFBB86FC)
                ) {
                    ModalBottomSheet()
                }
            }
        }
    }
}
//Ana ekranın tasarımı ve BottomSheet alanını açma işlevi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(scope: CoroutineScope, state: ModalBottomSheetState) {
    //Ekranın ana UI bölümüne "Open ..." butonunu ekleyelim
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.black)
            ),
            onClick = {
                scope.launch {
                    state.show()
                }
            }) {
            Text(text = "Open Modal Bottom Sheet Layout")
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheet() {
    val context= LocalContext.current
    //ModalBottomSheetValue hidden ise bu sınıf çağrıldığında ModalBottomSheet görünmeyecektir.
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        //ModalBottomSheet alanının köşelerini oval görünmesini sağlıyoruz.
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.padding(0.dp),

        //BottomSheet alanında gösterilecek UI tasarımını oluşturalım
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ){
                Column(modifier = Modifier.padding(10.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Do you want logout ?",fontSize = 25.sp,fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "You can login with same mobile number later.",color = Color.Gray)
                    Row(modifier = Modifier.padding(25.dp)) {
                        //Button görünümü ve tıklama event
                        OutlinedButton( onClick = { /*TODO*/ },
                            modifier = Modifier.padding(end = 32.dp)) {
                            Text("Cancel")
                        }

                        Button(onClick = {Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show() }) {
                            Text("Logout")
                        }
                    }
                }
            }

        }
    ) { //Ana ekrandaki button tıklama eventi ile ilişkilendirme
        MainScreen(coroutine, sheetState)
    }
}