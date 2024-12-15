package com.example.draftuas2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draftuas2.databinding.FragmentProfileAdminBinding
import com.example.draftuas2.databinding.FragmentProfileBinding

class ProfileFragmentAdmin : Fragment() {

    private var _binding: FragmentProfileAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menampilkan username langsung, pastikan username disertakan saat login
        val username = arguments?.getString("username") ?: "admin" // Gantilah "admin" jika ada input login
        binding.txtUsername.text = username

        // Menyusun aksi tombol logout dan clear
        binding.btnLogout.setOnClickListener {
            navigateToLogin()
        }
        binding.btnClear.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Menutup activity sebelumnya setelah berpindah
    }

    private fun navigateToRegister() {
        val intent = Intent(requireContext(), RegisterActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Menutup activity sebelumnya setelah berpindah
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
