import App from "./App";
import { AppRegistry } from "react-native";

const appName = "KakaoLogin";

AppRegistry.registerComponent(appName, () => App);
AppRegistry.runApplication(appName, {
  // Mount the react-native app in the "root" div of index.html
  rootTag: document.getElementById("root"),
});