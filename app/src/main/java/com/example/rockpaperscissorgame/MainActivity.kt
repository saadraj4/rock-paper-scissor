package com.example.rockpaperscissorgame
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rockpaperscissorgame.ui.theme.RockPaperScissorGameTheme
import androidx.compose.runtime.remember as remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RockPaperScissorGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}

@Composable
fun AppNav(){
    val navController = rememberNavController()

    var player1Choice = remember { mutableStateOf("") }
    var player2Choice = remember { mutableStateOf("") }
    var currentTurn = remember { mutableStateOf(1) }


    NavHost(navController = navController, startDestination = "game"){
        composable("game") {Game(navController, player1Choice, player2Choice, currentTurn)}
        composable("result"){ ResultScreen(navController, player1Choice, player2Choice)}
    }


}

@Composable
fun Game(navController: NavController, player1Choice: MutableState<String>, player2Choice: MutableState<String>, currentTurn: MutableState<Int>){
    var options = listOf("rock", "paper", "scissors");


    

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = if(currentTurn.value == 1){"Player 1 Turn"} else "Player 2 Turn", modifier = Modifier.padding(40.dp), style = TextStyle(fontSize = 40.sp))
            Row(horizontalArrangement = Arrangement.Center) {
                Button(onClick = { if(currentTurn.value == 1) {player1Choice.value = "rock"} else player2Choice.value = "rock" }) {
                    Image(
                        painter = painterResource(id = R.drawable.rock),
                        contentDescription = "rock",
                        modifier = Modifier
                            .width(50.dp)
                            .height(70.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { if(currentTurn.value == 1) {player1Choice.value = "paper"} else player2Choice.value = "paper"}) {
                    Image(
                        painter = painterResource(id = R.drawable.paper),
                        contentDescription = "paper",
                        modifier = Modifier
                            .width(50.dp)
                            .height(70.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { if(currentTurn.value == 1) {player1Choice.value = "scissor"} else player2Choice.value = "scissor"}) {
                    Image(
                        painter = painterResource(id = R.drawable.scissor),
                        contentDescription = "scissor",
                        modifier = Modifier
                            .width(50.dp)
                            .height(70.dp)
                    )
                }
            }
            Text(text = "Selected weapons: " + if(currentTurn.value == 1) player1Choice.value else player2Choice.value, style = TextStyle(fontSize = 24.sp))

            if (currentTurn.value == 1){
                Button(onClick = { currentTurn.value = 2 }) {
                    Text(text = "Let Player 2 Select", style = TextStyle(fontSize = 24.sp))
                }
            }else
                Button(onClick = { navController.navigate("result") }) {
                    Text(text = "Battle", style = TextStyle(fontSize = 24.sp))
                }
        }
    }
}

@Composable
fun ResultScreen(navController: NavController, player1Choice: MutableState<String>, player2Choice: MutableState<String>){

    val result = if (player1Choice == player2Choice) {
        "It's a tie!"
    } else if (
        (player1Choice.value == "rock" && player2Choice.value == "scissor") ||
        (player1Choice.value == "paper" && player2Choice.value == "rock") ||
        (player1Choice.value == "scissor" && player2Choice.value == "paper")
    ) {
        "Player 1 wins!"
    } else {
        "Player 2 wins!"
        }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)) {

        Text("Result" , style = TextStyle(fontSize = 60.sp))
        Spacer(modifier = Modifier.height(20.dp))

        Row() {
            Text("Player 1: " + player1Choice.value)

            //Image(painter = R.drawable., contentDescription = "p1")
        }
        Text("Player 2: " + player2Choice.value)

        Text(result)

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RockPaperScissorGameTheme {
        AppNav()
    }
}