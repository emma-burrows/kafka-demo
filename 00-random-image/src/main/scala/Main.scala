import java.io.File
import javax.imageio.ImageIO

object Main {
  def main(args: Array[String]) {
    args.head match {
      case "file"    =>
        RandomImage.writeFile("fuzzy", new RandomImage().generateImage())
      case "overlay" =>
        val img1 = ImageIO.read(new File("fuzzy.jpg"))
        val img2 = ImageIO.read(new File("../03-streams/kafka.png"))
        RandomImage.overlayImages(img1, img2)
    }
  }
}
