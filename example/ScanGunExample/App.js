import { StatusBar } from 'expo-status-bar';
import React, {useEffect, useState} from 'react';
import { NativeEventEmitter, NativeModules, StyleSheet, Text, View } from 'react-native';

import scanGun from 'react-native-external-scan-gun';


export default function App() {
  const [scanCode, setScanCode] = useState(scanCode);

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample);
    const eventListener = eventEmitter.addListener(
      scanGun.onScanCodeRecevieData,
      code => {
        setScanCode(code);
      },
    );
    return () => {
      eventListener.remove();
    };
  }, []);
  return (
    <View style={styles.container}>
      <Text style={styles.label}>本次扫码结果为:</Text>
      <Text style={styles.codeText}>{scanCode}</Text>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  label: {
    color: '#333333',
    marginBottom: 8,
  },
  codeText: {
    fontWeight: 'bold',
    color: '#108EE9'
  }
});
