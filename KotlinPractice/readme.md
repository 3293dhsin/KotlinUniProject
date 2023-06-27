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
      ){
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
        Text(text = "Row1")
    }
    </code>
  </pre>
</ol>
