package it.outlook.pietro.dan.dataset;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class DatasetTests {
	@Test
	public void isDetectedOnDisk_returns_false_when_file_not_on_disk() {
		Dataset dataset = new Dataset("testDataset", System.getProperty("user.dir"));
		assertEquals(dataset.isDetectedOnDisk(), false);
	}
	
	@Test
	public void isDetectedOnDisk_returns_true_when_file_on_disk() {
		String datasetName = "testDataset";
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset = new Dataset(datasetName, datasetPath);
		File datasetFile = new File(datasetPath + "/" + datasetName + ".json");
		try {
			datasetFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			assertEquals(dataset.isDetectedOnDisk(), true);
		} finally {
			datasetFile.delete();
		}
	}
	
	@Test 
	public void getData_returns_correct_json_when_setData_sets_empty_json_using_same_dataset() {
		String datasetName = "testDataset";
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset = new Dataset(datasetName, datasetPath);
		
		try {
			dataset.setData(new JSONObject());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			assertEquals(dataset.getData().isEmpty(), true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File datasetFile = new File(datasetPath + "/" + datasetName + ".json");
			datasetFile.delete();
		}
	}
	
	@Test 
	public void getData_returns_correct_when_setData_sets_generic_json_using_different_datasets_on_same_file() {
		String datasetName = "testDataset";
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset1 = new Dataset("testDataset", datasetPath);
		Dataset dataset2 = new Dataset("testDataset", datasetPath);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("testBoolean", false);
		jsonObject.put("testString", "test testing");
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(false);
		jsonArray.put("test");
		jsonObject.put("testArray", jsonArray);
		
		try {
			dataset1.setData(jsonObject);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			assertEquals(dataset2.getData().similar(jsonObject), true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File datasetFile = new File(datasetPath + "/" + datasetName + ".json");
			datasetFile.delete();
		}
	}
	
	@Test 
	public void getData_returns_correct_json_when_setData_sets_generic_json_using_same_dataset() {
		String datasetName = "testDataset";
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset = new Dataset(datasetName, datasetPath);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("testBoolean", false);
		jsonObject.put("testString", "test testing");
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(false);
		jsonArray.put("test");
		jsonObject.put("testArray", jsonArray);
		
		try {
			dataset.setData(jsonObject);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			assertEquals(dataset.getData().similar(jsonObject), true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File datasetFile = new File(datasetPath + "/" + datasetName + ".json");
			datasetFile.delete();
		}
	}
	
	@Test 
	public void getData_returns_correct_when_setData_sets_empty_json_using_different_datasets_on_same_file() {
		String datasetName = "testDataset";
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset1 = new Dataset("testDataset", datasetPath);
		Dataset dataset2 = new Dataset("testDataset", datasetPath);
		
		try {
			dataset1.setData(new JSONObject());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			assertEquals(dataset2.getData().isEmpty(), true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File datasetFile = new File(datasetPath + "/" + datasetName + ".json");
			datasetFile.delete();
		}
	}
	
	@Test
	public void getName_returns_correct_name() {
		String datasetName = "testDataset";
		Dataset dataset = new Dataset(datasetName, System.getProperty("user.dir") + "/" + "datasetTestFile.json");
		assertEquals(dataset.getName().compareTo(datasetName), 0);
	}
	
	@Test
	public void getPath_returns_correct_path() {
		String datasetPath = System.getProperty("user.dir");
		Dataset dataset = new Dataset("testDataset", datasetPath);
		assertEquals(dataset.getPath().compareTo(datasetPath), 0);
	}
}
