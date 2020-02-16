package com.team364.frc2020;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.simple.JSONObject;

/**
 * Write, Store, Read json files *simply* *
 * 
 * @param <K> K refers to Key
 * @param <V> V refers to Value
 */
public class JsonSimplifier<K, V> {
  /**
   * *PROGRAMMERS NOTE*(Colton) Wow pretty generic class right? get it, haha yea
   * im funny Anyway, the point of this class is to make it easy to write, store,
   * and read jsons. As an added bonus this class also converts to maps so logic
   * can be more effient.
   */

  String name;
  Map<K, V> map;
  Class<K> genericKey;
  Class<V> genericValue;
  Class<Map<K, V>> generic;
  String file;

  public JsonSimplifier(String name) {
    this.name = name;
    try {
      file = Files.readString(Paths.get(name));
    } catch (IOException e) {
      e.printStackTrace();
    }
    map = new Gson().fromJson(file, new TypeToken<Map<K, V>>() {}.getType());
  }

  public String getName() {
    return null;
  }

  public void writeElement(K key, V value) {
    map.put(key, value);
  }

  public void resetJson() {
    map.clear();
  }

  public int length() {
    return map.size();
  }

  public V get(String key, V whichElement) throws JSONException {
    return genericValue.cast(map.get(key));
  }

  public Map<K, V> getMap() {
    return map;
  }

  public void writeJson(boolean rewrite) {
    try {
      FileWriter fileWriter = new FileWriter(name, rewrite);
      JSONObject json = new JSONObject(map);
      fileWriter.write(json.toString());
      fileWriter.flush();
      fileWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
