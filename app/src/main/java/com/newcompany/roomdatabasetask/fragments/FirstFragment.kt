package com.newcompany.roomdatabasetask.fragments


import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.newcompany.roomdatabasetask.MainActivity
import com.newcompany.roomdatabasetask.MainActivityViewModel
import com.newcompany.roomdatabasetask.R
import com.newcompany.roomdatabasetask.databinding.FragmentFirstBinding
import java.io.File


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback{
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: FragmentFirstBinding
    val folder_main = "NewFolder"

    private  val fileUrl="https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        binding.lifecycleOwner = this
        viewModel=(activity as MainActivity).viewModel
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setPopulateSpinner(binding.spn, viewModel.getStaticSpinnerData())
        view.findViewById<Button>(R.id.btnList).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.btnNewsList).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_breakingNewsFragment)
        }
        view.findViewById<Button>(R.id.view_button).setOnClickListener {

            isStoragePermissionGranted()



        }


        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun download(url: String, directoryPath: String, fileName: String) {
        PRDownloader.download(
            url,
            directoryPath,
            fileName
        )
            .build()
            .setOnProgressListener {
                // Update the progress
                binding.progressBar.max = it.totalBytes.toInt()
                binding.progressBar.progress = it.currentBytes.toInt()
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    // Update the progress bar to show the completeness
                    binding.progressBar.max = 100
                    binding.progressBar.progress = 100

                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(activity, "Failed to download the $url", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(),WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                val f = File(Environment.getExternalStorageDirectory(), folder_main)
                if (!f.exists()) {
                    f.mkdirs()
                }
                download(fileUrl, f.parent + "/" + folder_main + "/", "myfiletest.pdf")

                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    1)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
            true
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
            //resume tasks needing this permission
        }
    }
}