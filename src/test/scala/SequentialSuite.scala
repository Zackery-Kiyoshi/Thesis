

import org.scalameter.api._


class SequentialSuite extends Bench.Group {
  override def persistor = Persistor.None
  
  include[SmallTestSeq]
  
}