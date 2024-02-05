@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.interfazpruebadamian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interfazpruebadamian.ui.theme.InterfazPruebaDamianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            InterfazPruebaDamianTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(
    images: List<Int> = listOf(
        R.drawable.arya_stark_post,
        R.drawable.jon_snow_post
    ),
    imagesStories: List<Int> = listOf(
        R.drawable.arya_stark,
        R.drawable.daenerys_targaryen,
        R.drawable.bran_stark,
        R.drawable.arya_stark,
        R.drawable.daenerys_targaryen,
        R.drawable.bran_stark
    ),
    names: List<String> = listOf("J0s3", "Madian")
){
    var shouldShowOnboarding = rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding.value) {
        MyLogin(onContinueClicked = { shouldShowOnboarding.value = false })
    } else {
        MyForum(images, imagesStories, names)
    }
}


//***************** PRIMERA PANTALLA ********************//
@Composable
fun MyLogin(onContinueClicked: () -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logoempresaandroid),
                contentDescription = "Logo de la empresa.png"
            )

            TextFieldEmailPassword()

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text("¿Has olvidado tu contraseña?")
            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Button (
                onClick = { onContinueClicked() },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Iniciar sesión")
            }

        }
    }
}
//***************** PRIMERA PANTALLA ********************//


//***************** TEXTFIELD EMAIL Y CONTRASEÑA ********************//
@Composable
fun TextFieldEmailPassword() {
    //***************** EMAIL ********************//
    var email = rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Email")},
    )
    //***************** EMAIL ********************//

    //***************** PASSWORD ********************//
    var password = rememberSaveable { mutableStateOf("") }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        singleLine = true,
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible.value)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector  = image, description)
            }
        }
    )
    //***************** PASSWORD ********************//
}
//***************** TEXTFIELD EMAIL Y CONTRASEÑA ********************//


//***************** SEGUNDA PANTALLA ********************//
@Composable
fun MyForum(images: List<Int>, imagesStories: List<Int>, names: List<String>){
    MyForImagesStories(imagesStories)
    MyForImages(images, names)
}
//***************** SEGUNDA PANTALLA ********************//


//***************** IMAGENES STORIES ********************//
@Composable
fun MyForImagesStories(imagesStories: List<Int>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(imagesStories) { imageStorie ->
            MyImagesStories(imageStorie)
        }
    }
}

@Composable
fun MyImagesStories(imageStorie: Int) {

    Box(modifier = Modifier
        .padding(22.dp)
        .clip(CircleShape)
        .size(85.dp)
    ) {
        Image(
            painter = painterResource(id = imageStorie),
            contentDescription = "Storie",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Green, CircleShape)
        )
    }

}
//***************** IMAGENES STORIES ********************//


//***************** SCROLL IMAGENES ********************//
@Composable
fun MyForImages(images: List<Int>, names: List<String>){
    LazyColumn(modifier = Modifier.padding(top = 125.dp)) {
        items(items = images.zip(names)) { (image, names) ->
            MyImagesItem(image, names)
        }
    }
}

@Composable
fun MyImagesItem(image: Int, name: String){

    Column(modifier = Modifier.fillMaxWidth()) {

        MyTopBarUser(image, name)

        //Imagen del usuario
        Image(
            painter = painterResource(id = image),
            contentDescription = "Imagen",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        MyIconsButton()

        MyDescription(name)

        Spacer(modifier = Modifier.padding(vertical = 8.dp))
    }
}


@Composable
fun MyTopBarUser(image: Int, name: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(image),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Magenta, CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text("Hace 3 minutos")
            }
        }

        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.baseline_more_vert_24),
                contentDescription = "Three Dots Horizontal"
            )
        }
    }
}


@Composable
fun MyIconsButton(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.outline_heart_check_24),
                    contentDescription = "Corazón"
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.outline_mode_comment_24),
                    contentDescription = "Comentario"
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.outline_send_24),
                    contentDescription = "Enviar"
                )
            }
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.twotone_save_alt_24),
                contentDescription = "Guardar"
            )
        }
    }
}

@Composable
fun MyDescription(name: String){
    Text(
        text = "Les gusta a $name y a cientos de personas más",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 14.dp)
    )
    Row {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(name)
                }
                append(" Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis accumsan auctor sapien, nec fermentum quam tristique at. In sagittis diam quis massa aliquam suscipit.")

            },
            modifier = Modifier.padding(horizontal = 14.dp)
        )
    }

}
//***************** SCROLL IMAGENES ********************//



