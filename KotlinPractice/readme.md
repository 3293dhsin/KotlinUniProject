<h1>Jetpack Compose UI</h1>

<h2>Layouts</h2>
<p>There are three commonly used layouts which are Row Layout, Column Layout and Box Layout.</p>
<ol>
  <li>Row Layout</li>
  <pre>
    <code>
      Row (
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
      ) {
        Text(text = "Row1")
        Text(text = "Row1")
      }
    </code>
  </pre>

  <li>Column Layout</li>
  <pre>
    <code>
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
          .fillMaxSize()   // .fillMaxHeight() or .fillMaxWidth() or a fixed size -> .size(400.dp)
          .background(Color.White)
      ) {
        Text(text = "Column1")
        Text(text = "Column2")
      }
    </code>
  </pre>

  <li>Box Layout</li>
  <pre>
    <code>
      Box (
        modifier = Modifier.fillMaxSize()
      ) {
        Text(
          text = "Hello $name!",
          color = Color.Red,
          fontSize = 30.sp,
          modifier = Modifier.align(Alignment.BottomCenter)
        )
        Text(
            text = "Some other text",
            color = Color.Red,
            fontSize = 30.sp
        )
      }
    </code>
  </pre>
</ol>
