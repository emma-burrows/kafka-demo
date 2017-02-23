import java.awt.image.BufferedImage
import java.io._
import java.util.concurrent.ThreadLocalRandom
import javax.imageio.ImageIO

class RandomImage {
  val width = 100
  val height = 100

  import RandomImage._

  def generateImageByteArray(): Array[Byte] = {
    convertImageToByteArray(generateImage())
  }

  def generateImage() = {
    val img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val tint = generatePixel(None)

    for (y <- 0 until height; x <- 0 until width) {
      img.setRGB(x, y, generatePixel(Option(tint)).pixel())
    }
    img
  }

  private def generatePixel(tint: Option[Pixel]) ={
    val red = ThreadLocalRandom.current().nextInt(0, 255)
    val green = ThreadLocalRandom.current().nextInt(0, 255)
    val blue = ThreadLocalRandom.current().nextInt(0, 255)

    tint match {
      case None => Pixel(red = red, green = green, blue = blue)
      case Some(tint) =>
        Pixel(
          red = (red + tint.red) / 2,
          green = (green + tint.green) / 2,
          blue = (blue + tint.blue) / 2
        )
    }
  }

  case class Pixel(alpha: Int = 255, red: Int, green: Int, blue: Int) {
    def pixel(): Int = (alpha << 24) | (red << 16) | (green << 8) | blue
  }
}

object RandomImage {
  // Write to file for manual testing of round-tripping
  def writeFile(name: String, img: BufferedImage) = {
    try {
      val f = new File(s"$name.png")
      ImageIO.write(img, "png", f);
    } catch {
      case e: IOException => println(e)
    }
  }

  def writeFile(name: String, img: Array[Byte]): Unit = {
    writeFile(name, convertByteArrayToImage(img))
  }

  def overlayImageByteArrays(backImageByteArray: Array[Byte], frontImageByteArray: Array[Byte]): Array[Byte] = {
    val backImage = convertByteArrayToImage(backImageByteArray)
    val frontImage = convertByteArrayToImage(frontImageByteArray)
    convertImageToByteArray(overlayImages(backImage, frontImage))
  }

  def overlayImages(backImage: BufferedImage, frontImage: BufferedImage): BufferedImage = {
    val combined = new BufferedImage(backImage.getWidth, backImage.getHeight, BufferedImage.TYPE_INT_ARGB)

    // paint both images, preserving the alpha channels
    val g = combined.getGraphics
    g.drawImage(backImage, 0, 0, null)
    g.drawImage(frontImage, 0, 0, null)
    combined
  }

  def convertByteArrayToImage(img: Array[Byte]) = {
    val in: InputStream = new ByteArrayInputStream(img)
    ImageIO.read(in)
  }

  def convertImageToByteArray(img: BufferedImage) = {
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream()
    ImageIO.write(img, "png", baos)
    baos.flush()
    val imageInByte = baos.toByteArray
    baos.close()
    imageInByte
  }
}
