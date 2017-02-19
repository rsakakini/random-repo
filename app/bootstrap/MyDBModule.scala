package bootstrap

import com.google.inject.AbstractModule
import services.FileLoader

class MyDBModule extends AbstractModule {

  override def configure: Unit = {
    bind(classOf[FileLoader]).asEagerSingleton()
  }

}