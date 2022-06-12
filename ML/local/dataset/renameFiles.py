import os

DATASET_PATH = "D:\Progetti\Profile\ML\local\dataset\speech2text" # Default folder "ML/dataset"


def rename(dataset_path):
    for i, (dirpath, dirnames, filenames) in enumerate(os.walk(dataset_path)):

        # ensure we're at sub-folder level
        if dirpath is not dataset_path:

            # save label (i.e., sub-folder name) in the mapping
            label = dirpath.split("/")[-1]
            print("\nProcessing: '{}'".format(label))

            # Counter for files
            counter = 0

            # process all audio files in sub-dir 
            for f in filenames:
                # Renaming the file
                new_name = label + "_" + str(counter) + ".wav"
                os.rename(label+"\\"+f, new_name)
                counter += 1


if __name__ == "__main__":
    rename(DATASET_PATH)
    print("Script ended")