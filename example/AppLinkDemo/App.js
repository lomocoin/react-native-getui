/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    View,
    Alert
} from 'react-native';
import {Container, Content, Card, CardItem, Text} from 'native-base';
import Getui from 'react-native-getui'

export default class AwesomeProject extends Component {
// 构造
    constructor(props) {
        super(props);
        // 初始状态
        this.state = {
            clientId: '',
            version:'',
            status:''
        };
    }

    componentDidMount() {
        this.updateComponentInfo()

        //  注册 VoIP 通知
       Getui.voipRegistration();

       //订阅消息通知
       var { NativeAppEventEmitter } = require('react-native');
       var resigsteClientIdSub = NativeAppEventEmitter.addListener(
         'registeClientId',
         (clientId) => {
           Alert.alert(clientId);
           this.setState({
                status:'已启动'
            })
         }
         
       )
       var receiveRemoteNotificationSub = NativeAppEventEmitter.addListener(
          'receiveRemoteNotification',
          (notification) => {
            //消息类型分为 APNs 和 payload 透传消息，具体的消息体格式会有差异
            switch (notification.type) {
                case "apns":
                    Alert.alert('APNs 消息通知',JSON.stringify(notification))
                    break;
                case "payload":
                    Alert.alert('payload 消息通知',JSON.stringify(notification))
                    break;
                case "AppLink":
                    Alert.alert('AppLinkPayload 消息通知',JSON.stringify(notification))
                    break;
                default:
            }
          }
        );

        var clickRemoteNotificationSub = NativeAppEventEmitter.addListener(
            'clickRemoteNotification',
            (notification) => {
                Alert.alert('点击通知',JSON.stringify(notification))
            }
        );

        var voipPushPayloadSub = 
        NativeAppEventEmitter.addListener(
            'voipPushPayload',
            (notification) => {
                Alert.alert('VoIP 通知： ',JSON.stringify(notification))
            }
        );
    }

    updateComponentInfo (){

      Getui.clientId((param)=> {
           this.setState({'clientId': param})
       })

       Getui.version((param)=> {
           this.setState({'version': param})
       })

       Getui.status((param)=> {
           let status = ''
           switch (param){
               case '0':
                   status = '正在启动'
                   break;
               case '1':
                   status = '已启动'
                   break;
               case '2':
                   status = '停止'
                   break;
           }
           this.setState({'status': status})
       })
    }

    componentWillUnMount() {
      //记得在此处移除监听
        receiveRemoteNotificationSub.remove()
        clickRemoteNotificationSub.remove()
        resigsteClientIdSub.remove()
        voipPushPayloadSub.remove()
    }


    render() {
        return (
            <View style={styles.container}>
                <Container>
                    <Content >
                        <Card>
                            <CardItem>
                                <Text>
                                    Version : {this.state.version}
                                </Text>
                            </CardItem>
                        </Card>
                        <Card>
                            <CardItem>
                                <Text>
                                    ClientId : {this.state.clientId}
                                </Text>
                            </CardItem>
                        </Card>
                        <Card>
                            <CardItem>
                                <Text>
                                    运行状态 : {this.state.status}
                                </Text>
                            </CardItem>
                        </Card>
                    </Content>

                </Container>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
        marginTop:20
    },
    content: {
        marginTop: 60
    }
});

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);
