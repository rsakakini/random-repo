package bootstrap

import com.google.inject.AbstractModule
import services.FileLoaderService

class MyDBModule extends AbstractModule {

  override def configure: Unit = {
    bind(classOf[FileLoaderService]).asEagerSingleton()
  }

}