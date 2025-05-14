package com.neilsayok.bluelabs.common.markdown


var MARKDOWN: String = """
`const` keyword is used to create immutable values, the only thing is these values needs to be defined at compile time.
😵‍💫😵‍💫😵‍💫😵‍💫😵‍💫🚀
`const` keyword has to be used with `val` keyword to create a variable. `const val` are only allowed on top level, in named objects, or in companion objects, which means it cannot be defined locally inside of functions and classes.


---

**Syntax for const:**


```kotlin
 const val <variable_name> = <value>
```


**Example:**


```kotlin
 const val name = "Sayok"
```


Variable defined with the `const` keyword needs to be initiated before the code is compiled, as the value stored in a `const` variable will be directly put in the source code during compilation.

To understand what is meant by the above line we need to see what happens when we use a  `const val` in Kotlin and decompile it in Java.


**Kotlin Code :**

```kotlin
object Constants {
    val name = "Sayok"
    const val petName = "Neil"
}

fun main() {
    println("Constants.name} Constants.petName}")
}
```


**Decompiled Java Code:**

```java
public final class Constants {

   public static final Constants INSTANCE = new Constants();
  
   private static final String name = "Sayok";
   
   public static final String petName = "Neil";

   public final String getName() {
      return name;
   }
}

 public static final void main() {
      System.out.println(Constants.INSTANCE.getName() + " Neil");
 }
```


So in the above code you will see the variable `petName`’s value is directly replaced in the print statement where the variable is used.


<mark>**\*Note-**</mark>`const val` only supports primitive data types as mentioned below":

| Supported data types | Supported data types |
|----|----|
| byte | byte |
| short | short |
| int | int |
| long | long |
| double | double |
| float | float |
| char | char |
| ![image info](https://images.pexels.com/photos/2071882/pexels-photo-2071882.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500) | ![image info](https://images.pexels.com/photos/2071882/pexels-photo-2071882.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500) |
| boolean | boolean |

| | |
|----|----|
| byte | byte |
| short | short |
| int | int |
| long | long |
| double | double |
| float | float |
| char | char |
| boolean | boolean |


![image info](https://images.pexels.com/photos/2071882/pexels-photo-2071882.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500)


"""


val HTML = """
    <html>
<p><code>const</code> keyword is used to create immutable values, the only thing is these values needs to be defined at compile time.
😵‍💫😵‍💫😵‍💫😵‍💫😵‍💫🚀
<code>const</code> keyword has to be used with <code>val</code> keyword to create a variable. <code>const val</code> are only allowed on top level, in named objects, or in companion objects, which means it cannot be defined locally inside of functions and classes.</p>
<p><strong>Syntax for const:</strong></p>
<pre><code class="lang-kotlin"> const val <span class="hljs-tag">&lt;<span class="hljs-name">variable_name</span>&gt;</span> = <span class="hljs-tag">&lt;<span class="hljs-name">value</span>&gt;</span>
</code></pre>
<p><strong>Example:</strong></p>
<pre><code class="lang-kotlin"> <span class="hljs-keyword">const</span> val name = <span class="hljs-string">"Sayok"</span>
</code></pre>
<p>Variable defined with the <code>const</code> keyword needs to be initiated before the code is compiled, as the value stored in a <code>const</code> variable will be directly put in the source code during compilation.</p>
<p>To understand what is meant by the above line we need to see what happens when we use a  <code>const val</code> in Kotlin and decompile it in Java.</p>
<p><strong>Kotlin Code :</strong></p>
<pre><code class="lang-kotlin"><span class="hljs-keyword">object</span> Constants {
    <span class="hljs-keyword">val</span> name = <span class="hljs-string">"Sayok"</span>
    const <span class="hljs-keyword">val</span> petName = <span class="hljs-string">"Neil"</span>
}

<span class="hljs-function"><span class="hljs-keyword">fun</span> <span class="hljs-title">main</span><span class="hljs-params">()</span></span> {
    println(<span class="hljs-string">"Constants.name} Constants.petName}"</span>)
}
</code></pre>
<p><strong>Decompiled Java Code:</strong></p>
<pre><code class="lang-java"><span class="hljs-keyword">public</span> <span class="hljs-keyword">final</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">Constants</span> </span>{

   <span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> Constants INSTANCE = <span class="hljs-keyword">new</span> Constants();

   <span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String name = <span class="hljs-string">"Sayok"</span>;

   <span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String petName = <span class="hljs-string">"Neil"</span>;

   <span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">final</span> String <span class="hljs-title">getName</span><span class="hljs-params">()</span> </span>{
      <span class="hljs-keyword">return</span> name;
   }
}

 <span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> <span class="hljs-keyword">void</span> <span class="hljs-title">main</span><span class="hljs-params">()</span> </span>{
      System.out.println(Constants.INSTANCE.getName() + <span class="hljs-string">" Neil"</span>);
 }
</code></pre>
<p>So in the above code you will see the variable <code>petName</code>’s value is directly replaced in the print statement where the variable is used.</p>
<p><mark><strong>*Note-</strong></mark><code>const val</code> only supports primitive data types as mentioned below&quot;:</p>
<table>
<thead>
<tr>
<th>Supported data types</th>
<th>Supported data types</th>
</tr>
</thead>
<tbody>
<tr>
<td>byte</td>
<td>byte</td>
</tr>
<tr>
<td>short</td>
<td>short</td>
</tr>
<tr>
<td>int</td>
<td>int</td>
</tr>
<tr>
<td>long</td>
<td>long</td>
</tr>
<tr>
<td>double</td>
<td>double</td>
</tr>
<tr>
<td>float</td>
<td>float</td>
</tr>
<tr>
<td>char</td>
<td>char</td>
</tr>
<tr>
<td>boolean</td>
<td>boolean</td>
</tr>
</tbody>
</table>

<button>Test</button>
    </html>
        """


val alltypes = """
### Solar System Exploration, 1950s – 1960s

- [ ] Reflection 
- [ ] Extention function 







### To Publish
- [x] higher-order-function
- [ ] expect-actual-function




# ATX Header 1 (ATX_1)

## ATX Header 2 (ATX_2)

### ATX Header 3 (ATX_3)

#### ATX Header 4 (ATX_4)

##### ATX Header 5 (ATX_5)

###### ATX Header 6 (ATX_6)

Setext Header 1 (SETEXT_1)
=========================

Setext Header 2 (SETEXT_2)
-------------------------

* Unordered List (UNORDERED_LIST)
  - List Item (LIST_ITEM)
  - With **Strong** (STRONG) and *Emph* (EMPH)

1. Ordered List (ORDERED_LIST)
2. List Item (LIST_ITEM)

> Block Quote (BLOCK_QUOTE)
> With [Link Text](http://example.com) (LINK_TEXT)

```kotlin
// Code Fence (CODE_FENCE)
fun main() = println("Code Block (CODE_BLOCK)")
```

    Indented Code Block (CODE_BLOCK)

`Inline Code Span (CODE_SPAN)`

<div>
    HTML Block (HTML_BLOCK)
    <img src="image.jpg" alt="Image (IMAGE)">
</div>

Paragraph (PARAGRAPH) with:
- Autolink: <http://example.com> (AUTOLINK)
- Full Reference Link: [Full Link][1] (FULL_REFERENCE_LINK)
- Short Reference Link: [Short Link][] (SHORT_REFERENCE_LINK)
- Image: ![Alt Text][img1] (IMAGE)

[1]: http://full.link "Link Title (LINK_TITLE)"
[Short Link]: http://short.link (LINK_DESTINATION)
[img1]: image.jpg "Image Title (LINK_TITLE)"

[Link Label]: http://label.link (LINK_DEFINITION)
Link Destination: http://destination.link (LINK_DESTINATION)





""".trimIndent()
