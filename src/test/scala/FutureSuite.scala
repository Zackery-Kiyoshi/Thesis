

import org.scalameter.api._

class FutureSuite extends Bench.Group {
  override def persistor = Persistor.None
  
  include[SmallTestFut]
  
}