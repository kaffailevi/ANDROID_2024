package ms.sapientia.kaffailevi.recipesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.auth0.android.jwt.JWT
import dagger.hilt.android.AndroidEntryPoint
import ms.sapientia.kaffailevi.recipesapp.R
import ms.sapientia.kaffailevi.recipesapp.databinding.FragmentProfileBinding
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.NewRecipeViewModel
import ms.sapientia.kaffailevi.recipesapp.repository.recipe.viewmodel.ProfileViewModel
import ms.sapientia.kaffailevi.recipesapp.service.TOKEN
import ms.sapientia.kaffailevi.recipesapp.ui.recipe.RecipeAdapter
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.userEmail.text = JWT(TOKEN).getClaim("email").asString()

        val myRecipeCountView :TextView  = binding.myRecipeCount;

        profileViewModel.myRecipeList.observe(viewLifecycleOwner){
            myRecipeCountView.text = "Your recipe count: ${it.size}"
        }
        profileViewModel.loadMyRecipes()
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_newRecipeFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


