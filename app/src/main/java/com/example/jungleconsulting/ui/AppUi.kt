package com.example.jungleconsulting.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.jungleconsulting.model.UserDataModel
import com.example.jungleconsulting.util.isInternetAvailable
import java.io.File

@Composable
fun AppUi(viewModel: AppViewModel) {
    val context = LocalContext.current
    fun noConnection() = Toast.makeText(context, "No connection!", Toast.LENGTH_LONG).show()
    val connection = remember { mutableStateOf(context.isInternetAvailable()) }
    if (!connection.value) noConnection()
    LaunchedEffect(Unit) {
        viewModel.checkConnection(connection.value)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            textAlign = TextAlign.Center,
            text = "Jungle Consulting",
            fontSize = 30.sp
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(viewModel.users) { index, item ->
                UserItem(item, context, viewModel, connection.value)
                if (index == viewModel.users.size - 5) if (connection.value) viewModel.getUsersFromService() else noConnection()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(item: UserDataModel, context: Context, viewModel: AppViewModel, connection: Boolean) {
    val imagesCacheDir by remember { mutableStateOf(File(context.filesDir, "images_cache")) }
    val imageFileName by remember { mutableStateOf(item.image) }
    val imageFile by remember { mutableStateOf(File(imagesCacheDir, imageFileName)) }
    val imageBitmap by remember { mutableStateOf(BitmapFactory.decodeFile(imageFile.absolutePath).asImageBitmap()) }
    var openDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        onClick = {
            openDialog = true
        }
    ) {
        Row {
            Image(
                bitmap = imageBitmap,
                contentDescription = item.login,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(64.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                text = item.login,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 30.sp
            )
        }
    }
    if (openDialog) UserDialog(
        viewModel = viewModel,
        user = item,
        imageBitmap = imageBitmap,
        connection = connection,
        dismiss = { openDialog = false })
}

@Composable
fun UserDialog(
    viewModel: AppViewModel,
    user: UserDataModel,
    connection: Boolean,
    imageBitmap: ImageBitmap,
    dismiss: () -> Unit
) {
    viewModel.getFirstRepositoriesFromService(user.login, connection)

    Dialog(
        onDismissRequest = { dismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f),
            border = BorderStroke(width = 2.dp, color = Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = user.login, fontSize = 24.sp, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                Image(
                    bitmap = imageBitmap,
                    contentDescription = user.login,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {
                    itemsIndexed(viewModel.repositories) { index, item ->
                        Text(
                            modifier = Modifier.padding(start = 18.dp, bottom = 20.dp),
                            text = item.name,
                            color = Color.Black
                        )
                        if (index == viewModel.repositories.size - 5) if (connection) viewModel.getRepositoriesFromService(
                            user.login
                        )
                    }
                }
            }
        }
    }
}