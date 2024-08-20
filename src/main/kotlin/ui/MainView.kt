package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import controller.TranslatedLinesController
import controller.VocabWordsController
import domain.FileType
import domain.Language
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import util.printToGui
import java.awt.FileDialog
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun MainView() {

//    val SUPPORTED_FILE_TYPES = arrayOf(FileChooser.ExtensionFilter("Text file", "*.txt"))
    val LANGUAGES: List<Language> = Language.entries
    val FILETYPES: List<FileType> = FileType.entries
//
//    val translatedLinesController: TranslatedLinesController by inject()
//    val vocabWordsController: VocabWordsController by inject()
    val translatedLinesController = koinInject<TranslatedLinesController>()
    val vocabWordsController = koinInject<VocabWordsController>()
//    val inputFormatToggle = ToggleGroup()
    var file: File? = null
    var directory: String? = null
//

    MaterialTheme {
        var expanded by remember { mutableStateOf(false) }
        var selectedLanguage by remember { mutableStateOf(LANGUAGES[0]) }
        var selectedFileType by remember { mutableStateOf(FILETYPES[0]) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                val fd =  FileDialog(ComposeWindow())
                fd.isVisible = true
                val files: Array<File> = fd.files

                if (files.isNotEmpty() && files[0].isFile) {
                    file = files[0]
                    directory = fd.directory
                }
            } ) {
                Text("Choose file")
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedLanguage.toString(),
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    LANGUAGES.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedLanguage = selectionOption
                                expanded = false
                            }
                        ){
                            Text(text = selectionOption.toString())
                        }
                    }
                }
            }

            FILETYPES.forEach { option ->
                Text(option.toString(), )
                RadioButton(
                    selected = selectedFileType == option,
                    onClick = {
                        selectedFileType = option
                    },
                    modifier = Modifier.selectable(
                        selected = selectedFileType == option,
                        onClick = { selectedFileType = option }
                    )
                )
            }

            Button(onClick = {
                if (file != null && directory != null) {
                    if (selectedFileType == FileType.LINES) {
                        translatedLinesController.buildCsvFromLines(file!!, directory!!, selectedLanguage)
                    } else {
                        vocabWordsController.buildCsvFromVocabularyWords(file!!, directory!!, selectedLanguage)
                    }
                } else {
                    printToGui("Something is null")
                }
            } ) {
                Text("Convert")
            }
        }
    }
}